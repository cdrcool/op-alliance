import React from 'react'
import {renderRoutes} from 'react-router-config'
import {BrowserRouter as Routers} from 'react-router-dom'
import routes from './router'

const App = () => {
    return (
        <Routers>
            {renderRoutes(routes)}
        </Routers>
    )
}
export default App;