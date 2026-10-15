# Mini Chat de Terminal em Java utilizando Sockets

Mini aplicação de chat desenvolvida em Java utilizando a API de Sockets para comunicação via terminal.

---

## Arquitetura Base

A estrutura e a lógica de comunicação cliente-servidor foram inspiradas no repositório:
- [Repositório Base - java_socket_chat](https://github.com/nuromirzak/java_socket_chat/)

---

## Materiais de Apoio e Referências

Para o desenvolvimento e estudo da arquitetura de Sockets em Java, foram consultados os seguintes materiais:
1. [GeeksforGeeks - Socket Programming in Java](https://www.geeksforgeeks.org/java/socket-programming-in-java/)
2. [GeeksforGeeks - Simple Chat Application using Sockets in Java](https://www.geeksforgeeks.org/java/simple-chat-application-using-sockets-in-java/)
3. [Baeldung - A Guide to Java Sockets](https://www.baeldung.com/a-guide-to-java-sockets)

---

## Como Executar

O projeto foi executado utilizando a IDE IntelliJ IDEA.

1. Abra o projeto no IntelliJ IDEA.
2. Execute a classe responsável pelo Servidor (`Server.java`).
3. Execute a classe responsável pelo Cliente (`Client.java`). É possível executar mais de uma instância do cliente para simular a troca de mensagens.

Alternativamente, também é possível executar o projeto diretamente pelo terminal:

1. Altere o diretório:
    ```bash
    cd src/main/java
    ```
2. Compile os arquivos e inicie o servidor:
   ```bash
   javac *.java
   java Server
   ```
3. Em terminais diferentes inicie os clientes:
    ```bash
   java Client.java
    ```

Nota: Este README foi gerado com auxílio de Inteligência Artificial (Gemini).
