# Attempt at making a Graph RAG model

## To set up the Docker container for creating a Neo4j database:
- First command:
```bash
docker pull neo4j
```
- Second command:
```bash
docker run --name neo4j -d -p 7474:7474 -p 7687:7687 -v /neo4j/data:/data -v /neo4j/logs:/logs -e NEO4J_AUTH=neo4j/neo4j neo4j
```