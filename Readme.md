# IPL XPTO Tracking Telemetry

O objectivo deste microserviço é o gerenciamento de perfis de telemetria e sensores
Foi desenvolvido usando o Framework Spring, especificamente o Spring Boot, com um banco de dados PostgreSQL, e demais dependencias conforme o projeto modelo original.

## Uso

Para utilizar este projeto, basta clonar o repositório e importá-lo para o seu IDE preferido. Antes de executar a aplicação, certifique-se de que você tem o PostgreSQL instalado e crie um novo banco de dados para este projeto. Você pode então configurar a conexão com o banco de dados editando o arquivo `application.properties` localizado na pasta `src/main/resources`.

### Configuração

Para alterar a porta da API REST, edite o arquivo `application.properties` e defina a propriedade `server.port` para o número da porta desejado. Por padrão, o número da porta é definido como `8080`.

Para alterar a URL de conexão do PostgreSQL, edite o arquivo `application.properties` e defina a propriedade `spring.datasource.url` para a URL desejada. Por padrão, a URL é definida como `jdbc:postgresql://localhost:5432/xpto_telemetry`.

### Testes

Uma coleção do Postman é fornecida na pasta `\postman` deste projeto, que contém um conjunto de solicitações de amostra que podem ser usadas para testar a API REST. Importe a coleção para o Postman para começar.

## Endpoints

Os seguintes endpoints estão disponíveis nesta API REST:

### GET /tracking/sensors

Retorna uma lista de todos os sensores no sistema.

### GET /tracking/sensors/{id}

Retorna o sensor com o ID especificado.

### POST /tracking/sensors

Cria um novo sensor com os detalhes especificados.

### PUT /tracking/sensors/{id}

Atualiza os detalhes do sensor com o ID especificado.

### DELETE /tracking/sensors/{id}

Exclui o sensor com o ID especificado.

### GET /tracking/telemetryprofiles

Retorna uma lista de todos os perfis no sistema.

### GET /tracking/telemetryprofiles/{id}

Retorna o perfil com o ID especificado.

### POST /tracking/telemetryprofiles

Cria um novo perfil com os detalhes especificados.

### PUT /tracking/telemetryprofiles/{id}

Atualiza os detalhes do perfil com o ID especificado.

### DELETE /tracking/telemetryprofiles/{id}

Exclui o perfil com o ID especificado.