Feature: CoinMarket landing page feature

#@UITest
#Scenario: Login page title
#Given I am on the homepage
#When I gets the title of the page
#Then page title should be "Cryptocurrency Prices, Charts And Market Capitalizations | CoinMarketCap"
#Then I select "100" from the showrows option
#And I validate "100" rows are displayed


@UITest
Scenario: Filter Verification
Given I am on the homepage
When I gets the title of the page
Then page title should be "Cryptocurrency Prices, Charts And Market Capitalizations | CoinMarketCap"
And I open filter dialog
And I select filter criteria
| filtername | MinRange | MaxRange |
| MarketCap | $1,000,000,000 | $10,000,000,000 |
| price | $1 | $100 |
And I validate filtering results are correct