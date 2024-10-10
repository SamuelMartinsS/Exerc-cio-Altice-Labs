# Sequencia

## Como iniciar a aplicação (Netbeans)

1. Nas propriedades de 'run' do projeto deve ser registado o argumento 'server'
2. Iniciar o programa através da opção 'Run' do IDE
3. Verificar o url `http://localhost:8081/healthcheck`
4. Entrar no url `http://localhost:8080/labseq/n` sendo n um número inteiro não negativo

## Front-End

Após iniciar o servidor com sucesso, este pode ser testado através do documento frontend.html. Neste, existe um pequeno formulário onde o utilizador pode escolher o valor _n_ da sequência de labseq.

## Open API

A descrição dos endpoints e do resultado esperado, está descrita nos documentos openapi.JSON e openapi.Yaml. Estes documentos podem ser importados para o editor web do Swagger em https://editor.swagger.io/

## Descrição da aplicação

    labseq\src\main\java\com\my\labseq\resources

Na diretoria acima descrita, foi criada uma classe chamada de `labseqResource` onde está declarado o endpoint @GET

    /labseq/{n}

Existe também uma função responsável por gerar a sequência de `labseq generateSequence(int n)`. Esta função leva o termo synchronized para prevenir que haja problemas de concorrência. A função permite gerar os numeros da sequência labseq até ao Index _n_, mas caso este já tenha sido calculado a função não vai gerar nenhum nenhum número da sequência.
