from flask import Flask, request, jsonify
from transformers import pipeline, AutoTokenizer, AutoModel
from neo4j import GraphDatabase
from scipy.spatial.distance import cosine
import numpy as np

app = Flask(__name__)

# HuggingFace Models
tokenizer = AutoTokenizer.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")
model = AutoModel.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")
qa_pipeline = pipeline("question-answering", model="distilbert-base-uncased-distilled-squad")

# Neo4j Database Connection
NEO4J_URI = "bolt://localhost:7687"
NEO4J_USER = "neo4j"
NEO4J_PASSWORD = "valmik@neo4j7"
driver = GraphDatabase.driver(NEO4J_URI, auth=(NEO4J_USER, NEO4J_PASSWORD))

def generate_embedding(text):
    inputs = tokenizer(text, return_tensors="pt", padding=True, truncation=True)
    outputs = model(**inputs)
    return outputs.pooler_output[0].tolist()

def find_relevant_context(query_embedding, max_results=5):
    """
    Query Neo4j to find relevant nodes based on cosine similarity.
    """
    with driver.session() as session:
        cypher_query = """
        MATCH (n)
        WHERE n.vectorEmbedding IS NOT NULL
        RETURN n.title AS title, n.content AS content, n.vectorEmbedding AS embedding, 
               labels(n) AS labels
        """
        results = session.run(cypher_query)

        # Compute cosine similarity and find top results
        candidates = []
        for record in results:
            embedding = np.array(record["embedding"])
            similarity = 1 - cosine(query_embedding, embedding)
            candidates.append((record, similarity))

        # Sort by similarity score and return top results
        candidates = sorted(candidates, key=lambda x: x[1], reverse=True)
        return candidates[:max_results]

@app.route('/embedding', methods=['POST'])
def embedding():
    data = request.json
    text = data['text']
    embedding = generate_embedding(text)
    return jsonify(embedding)

@app.route('/qa', methods=['POST'])
def question_answer():
    """
    Handles QA queries by finding relevant context in Neo4j and passing it to the QA pipeline.
    """
    data = request.json
    query = data["query"]

    # Step 1: Generate embedding for the user query
    query_embedding = generate_embedding(query)

    # Step 2: Retrieve relevant context from Neo4j
    relevant_contexts = find_relevant_context(query_embedding)
    if not relevant_contexts:
        return jsonify({"error": "No relevant context found in the graph database"}), 404

    # Step 3: Combine relevant contexts
    combined_context = " ".join(
        [
            f"{record[0]['labels'][0]}: {record[0]['title'] if record[0]['title'] else ''} "
            f"{record[0]['content'] if record[0]['content'] else ''}"
            for record in relevant_contexts
        ]
    )

    # Step 4: Run QA pipeline
    result = qa_pipeline(question=query, context=combined_context)
    return jsonify(result)

@app.route('/')
def home():
    return "<h1>HELLO</h1>"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
