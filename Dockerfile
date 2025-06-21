
# usar uma imagem oficial do do java JDK 17
FROM openjdk:17-jdk-slim

# criar um diretorio dentro do conteiner para o app
WORKDIR /app

# copiar o arquivo .jar para dentro do conteiner
COPY target/*.jar app.jar

# rodar o aplicativo
ENTRYPOINT  ["java", "-jar", "app.jar"]