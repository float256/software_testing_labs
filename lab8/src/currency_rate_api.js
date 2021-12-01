export const RESPONSE_FOR_CURRENCIES = {
    USD: {
        timestamp: Date.now(),
        base_currency: 'USD',
        rate: {
            EUR: 0.88352,
            RUB: 74.21822
        }
    },
    EUR: {
        timestamp: Date.now(),
        base_currency: 'EUR',
        rate: {
            USD: 1.13191,
            RUB: 83.98017
        }
    },
    RUB: {
        timestamp: Date.now(),
        base_currency: 'RUB',
        rate: {
            USD: 0.01347,
            EUR: 0.01191
        }
    }
}
export const SUPPORTED_CURRENCIES = ['USD', 'EUR', 'RUB']
export const CURRENCY_RATE_API_STUB = generateCurrencyRateAPIStub()

function generateCurrencyRateAPIStub() {
    let endpointPerEachCurrency = Object.entries(RESPONSE_FOR_CURRENCIES).map(
        ([base_currency, currency_rates]) => wrapResponse('GET', `/currency/${base_currency}`, currency_rates)
    )
    return [
        wrapResponse('GET', '/allCurrencies', SUPPORTED_CURRENCIES),
        ...endpointPerEachCurrency
    ]
}

function wrapResponse(method, endpoint, responseBody) {
    return {
        predicates: [{
            equals: {
                method: method,
                path: endpoint
            }
        }],
        responses: [
            {
                is: {
                    statusCode: 200,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(responseBody)
                }
            }
        ]
    }
}