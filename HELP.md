# Gerando o Build Final

 - Conclua os testes e faça o versionamento final do aplicativo;
 - No terminal do Intellij,gere o _build_ comandando:

```cmd
mvn clean package -DskipTests
```

> **Observação!**
>  - É necessário ter o Maven instalado e registrado nas variáveis de ambiente.

Será gerado um arquivo `target/springpad-0.0.1-SNAPSHOT.jar` que é o build final.

> **Dica!**
> - Você pode renomear este arquivo, por exemplo para `springpad.jar`;
> - Você pode mover este arquivo para qualquer local dentro dservidor.

Para executar o serviço, abra um prompt no local onde salvou o arquivo e comande:

```cmd
java -jar springpad-0.0.1-SNAPSHOT.jar
```
Ou, se renomeou:
```cmd
java -jar springpad.jar
```

---