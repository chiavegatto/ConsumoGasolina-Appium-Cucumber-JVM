#encoding: Cp1252
Feature: Consumo de gasolina com desempenho mensal
  Como usuário do sistema Consumo de Gasolina
  Desejo cadastrar, alterar, visualizar e excluir Abastecimentos 
  Então conseguirei realizar as operações na funcionalidade Abastecimentos.

  Scenario: Cadastrar abastecimento com a data atual do sitema
    Given estou acessando a aplicação Consumo Gasolina
    When acesso o menu "Adicionar Abastecimento"
    And preencho o abastecimento com os seguintes valores:
      | KM Abastecimento | Quantidade Litros | Valor  |
      | 22950            | 34.14             | 106.86 |
    And pressiono o botão "Cadastrar"
    Then deve ser exibido as seguintes informações no resumo:
      | KM Litro            | Valor Abastecimentos | Quantidade Abastecimentos | Quantidade Litros  | Quantidade KM |
      | 0.0 KM/Litro(s)/Mês | R$ 106.86            | 1 Vez(es)/Mês             | 34.14 Litro(s)/Mês | 0 KM/Mês      |

  Scenario: Cadastrar abastecimento com a data diferente da data atual do sitema
    Given estou acessando a aplicação Consumo Gasolina
    When acesso o menu "Adicionar Abastecimento"
    And preencho o abastecimento com os seguintes valores:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 22950            | 34.14             | 106.86 | 15/03/2015         |
    And pressiono o botão "Cadastrar"
    Then deve ser exibido as seguintes informações no resumo:
      | KM Litro            | Valor Abastecimentos | Quantidade Abastecimentos | Quantidade Litros  | Quantidade KM |
      | 0.0 KM/Litro(s)/Mês | R$ 106.86            | 1 Vez(es)/Mês             | 34.14 Litro(s)/Mês | 0 KM/Mês      |

  Scenario: Editar um abastecimento cadastrado no sistema
    Given estou acessando a aplicação Consumo Gasolina
    And possuo o abastecimento cadastrado com as seguintes informações:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 22950            | 34.14             | 106.86 | 15/03/2015         |
    When acesso o menu "Ver Abastecimentos"
    And pressiono no abastecimento com a data "15/03/2015"
    And preencho o abastecimento com os seguintes valores:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 23400            | 44.12             | 140.30 | 15/03/2015         |
    And pressiono o botão "Salvar"
    Then deve ser exibido o abastecimento com as seguintes informações:
      | KM Abastecimento | Quantidade Litros | Valor | Data Abastecimento |
      | 23400            | 44.12             | 140.3 | 15/03/2015         |

  Scenario: Excluir um abastecimento cadastrado no sistema
    Given estou acessando a aplicação Consumo Gasolina
    And possuo o abastecimento cadastrado com as seguintes informações:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 22950            | 34.14             | 106.86 | 15/03/2015         |
    When acesso o menu "Ver Abastecimentos"
    And pressiono longamente no abastecimento com a data "15/03/2015"
    And confirmo a exclusão do abastecimento
    Then deve ser exibido a mensagem "Nenhum abastecimento cadastrado." na listagem de abastecimentos

  Scenario: Verificar se está sendo feito o cálculo do desempenho mensal
    Given estou acessando a aplicação Consumo Gasolina
    And possuo o abastecimento cadastrado com as seguintes informações:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 22950            | 34.14             | 106.86 | 15/03/2015         |
    When acesso o menu "Adicionar Abastecimento"
    And preencho o abastecimento com os seguintes valores:
      | KM Abastecimento | Quantidade Litros | Valor | Data Abastecimento |
      | 23400            | 44.12             | 140.3 | 15/03/2015         |
    And pressiono o botão "Cadastrar"
    Then deve ser exibido as seguintes informações no resumo:
      | KM Litro              | Valor Abastecimentos | Quantidade Abastecimentos | Quantidade Litros  | Quantidade KM |
      | 13.18 KM/Litro(s)/Mês | R$ 247.16            | 2 Vez(es)/Mês             | 78.26 Litro(s)/Mês | 450 KM/Mês    |

  Scenario: Verificar o desempenho dos meses anteriores
    Given estou acessando a aplicação Consumo Gasolina
    And possuo o abastecimento cadastrado com as seguintes informações:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 22950            | 34.14             | 106.86 | 01/02/2015         |
      | 23400            | 44.12             | 140.3  | 15/02/2015         |
    When acesso o menu "Meses Anteriores"
    And Preencho a data inicial "01/02/2015" e data final "28/02/2015"
    And seleciono o tipo de visualização "Desempenho"
    And pressiono o botão "Pesquisar"
    Then deve ser exibido as seguintes informações no resumo:
      | KM Litro          | Valor Abastecimentos | Quantidade Abastecimentos | Quantidade Litros | Quantidade KM |
      | 13.18 KM/Litro(s) | R$ 247.16            | 2 Vez(es)                 | 78.26 Litros      | 450 KM        |

  Scenario: Verificar se está listando os abastecimentos dos meses anteriores
    Given estou acessando a aplicação Consumo Gasolina
    And possuo o abastecimento cadastrado com as seguintes informações:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 22950            | 34.14             | 106.86 | 01/02/2015         |
      | 23400            | 44.12             | 140.3  | 15/02/2015         |
    When acesso o menu "Meses Anteriores"
    And Preencho a data inicial "01/02/2015" e data final "28/02/2015"
    And seleciono o tipo de visualização "Abastecimento"
    And pressiono o botão "Pesquisar"
    Then deve ser exibido os abastecimentos:
      | KM Abastecimento | Quantidade Litros | Valor  | Data Abastecimento |
      | 22950            | 34.14             | 106.86 | 01/02/2015         |
      | 23400            | 44.12             | 140.3  | 15/02/2015         |
