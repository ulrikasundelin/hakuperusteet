import React from 'react'
import _ from 'lodash'

import {logOut} from './GoogleAuthentication'

export default class GoogleSession extends React.Component {
  render() {
    const state = this.props.state
    const controller = this.props.controller

    return <div id="googleAuthentication">
      <img id="googleAuthenticationStatus" src="/hakuperusteet/img/button_google_signedin.png" />
      <a id="logout" href="#" onClick={logOut(state, controller)}>Log out</a>
    </div>
  }
}
