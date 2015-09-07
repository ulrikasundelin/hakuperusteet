import React from 'react'
import _ from 'lodash'

import {googleAuthenticationRenderFailure} from './util/GoogleAuthentication'

export default class GoogleAuthentication extends React.Component {
  componentDidMount() {
    gapi.signin2.render('googleAuthentication', {
      'width': 250,
      'height': 50,
      'longtitle': true,
      'theme': 'light',
      'onfailure': googleAuthenticationRenderFailure
    })
  }

  render() {
    return <div id="googleAuthentication" />
  }
}
