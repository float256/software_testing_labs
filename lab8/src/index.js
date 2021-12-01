import { create } from 'mountebank'
import { CURRENCY_RATE_API_STUB } from './currency_rate_api.js'
import { MOUNTEBANK_PORT, CURRENCY_RATE_MOCK_PORT } from './settings.js'
import fetch from 'node-fetch'

export function initImposter(mountebankPort, imposterPort, protocol, stubs) {
    let body = {
        port: imposterPort,
        protocol: protocol,
        stubs: stubs
    }
    return fetch(`http://localhost:${mountebankPort}/imposters`,
        {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        })
}

create({
    port: MOUNTEBANK_PORT,
    pidfile: '../mb.pid',
    logfile: '../logs/mb.log',
    protofile: '../protofile.json',
    ipWhitelist: ['*']
})
initImposter(MOUNTEBANK_PORT, CURRENCY_RATE_MOCK_PORT, 'http', CURRENCY_RATE_API_STUB)