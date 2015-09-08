import React from 'react'

export default class UserDataInput extends React.Component {
  render() {
    const controller = this.props.controller
    const title = this.props.title
    const name = this.props.name
    return <div className="userdataFormFow">
        <label htmlFor={name}>{title}</label>
        <input type="text" id={name} name={name} onChange={controller.valueChanges}/>
      </div>
  }
}
