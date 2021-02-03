# Ferramenta para votação de assembleias de Cooperados
Esta é uma ferramenta desenvolvida em Java, com o objetivo de permitir a criação de votações para resolução de uma pauta de uma assembleia.

## Requisitos
A ferramenta foi desenvolvida utilizando Java 11 e apresenta os seguintes requisitos para poder ser compilada e executada:
* Git
* Maven 3.6.3 ou superior
* Java 11 ou superior
## Considerações
Esta ferramenta utiliza um banco H2, configurado para ser escrito em uma pasta "data/" dentro da estrutura do projeto. Assim, ao executar o projeto, o usuário deve ter permissão para tal.
Os logs da execução também serão escritos em uma pasta dentro da estrutura do projeto, na pasta "logs/".

### Instalação do projeto
Ao realizar o clone do projeto, utilizar os seguintes comandos no CMD de escolha:
```
$ cd cooperative_poll
$ mvn clean install
```
Este comando irá baixar as dependências do projeto e construí-lo, de forma que poderá ser executado, tando pelo comando do maven quanto pelas IDEs de mercado.

### Swagger
A documentação sobre os endpoints do projeto pode ser encontrada na página do Swagger.
Por padrão, é possível acessar a página pelo endereço: http://localhost:8080/swagger-ui.html.

### Estrutura do Banco
Não é preciso realizar nenhuma operação para montar a estrutura do banco.
O projeto conta com o FlayWay para construir as tabelas e adicionar os dados básicos para o funcionamento.
Ao iniciar a aplicação, o FlyWay irá acessar a base e verificar se os scripts salvos já foram executados e realizar os procedimentos necessários.

### Como utilizar o sistema
Ao executar a aplicação, será necessário utilizar uma ferramenta para enviar as requisições REST, como o POSTMAN ou o cUrl.
A página do Swagger do projeto pode ser utilizada para realizar os envios das requisições de teste.

O primeiro passo recomendado é utilizar o método POST do endpoint de usuários, para a criação de um associado na base da aplicação. Para isto, será necessário cadastrar um CPF e o nome do associado, além de uma senha.
Este passo é importante pois a requisição em que o voto é realizado está com a autenticação Basic habilitada, necessitando do número do CPF e da senha do usuário que irá votar.
Estas operações estão contidas no endpoint: UserController.

Após isto, é possível utilizar o endpoint de reunião/pauta para criar uma nova reunião/pauta. Este POST necessita somente do assunto da reunião/pauta.
Estas operações estão contidas no endpoint: MeetingController.

Com uma reunião criada, é possível acessar o método POST do endpoint sessão de votação e iniciá-la.
Para isto, será preciso informar o id da reunião e o tempo, em segundos, em que a votação deve ser permitida.
Estas operações estão contidas no endpoint: MeetingController.

A votação será realizada por um método POST no endpoint de votação.
Este endpoint está protegido por um login, pelo qual será determinado qual usuário está tentando realizar o voto.
Para isso, será preciso informar o id da reunião em serpa computado o voto e  o id da opção desejada para o voto.
Estas operações estão contidas no endpoint: VoteController.

Existe um endpoint com um método GET com a intenção de expor as opções de voto permitidas no sistema.
Estas operações estão contidas no endpoint: VoteController.

Para buscar o resultado de uma votação, existe uma operação com o método GET retornando o Status da votação e a quantidade de votos para cada uma das opções.
Para isto, será preciso passar o id da reunião/pauta como uma parâmetro da requisição.
Estas operações estão contidas no endpoint: MeetingController.