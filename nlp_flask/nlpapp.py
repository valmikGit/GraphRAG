from flask import Flask, request, jsonify
from transformers import pipeline, AutoTokenizer, AutoModel

app = Flask(__name__)

# HuggingFace Models
tokenizer = AutoTokenizer.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")
model = AutoModel.from_pretrained("sentence-transformers/all-MiniLM-L6-v2")
qa_pipeline = pipeline("question-answering", model="distilbert-base-uncased-distilled-squad")

def generate_embedding(text):
    inputs = tokenizer(text, return_tensors="pt", padding=True, truncation=True)
    outputs = model(**inputs)
    return outputs.pooler_output[0].tolist()

@app.route('/embedding', methods=['POST'])
def embedding():
    data = request.json
    text = data['text']
    embedding = generate_embedding(text)
    return jsonify(embedding)

@app.route('/qa', methods=['POST'])
def question_answer():
    data = request.json
    query = data['query']
    context = data['context']
    result = qa_pipeline(question=query, context=context)
    return jsonify(result)

@app.route('/')
def home():
    return "<h1>HELLO</h1>"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
