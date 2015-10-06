import {expect, done} from 'chai'
import {openPage, hakuperusteetLoaded, testFrame, logout, takeScreenshot, S, S2} from './testUtil.js'

describe('Page without session', () => {
  before(openPage("/hakuperusteet", hakuperusteetLoaded))

  it('should show Google login button', () => {
    return S2(".googleAuthentication.login").then(assertOneElementFound).then(done).catch(done)
  })
  it('should not show Google session', () => {
    expect(S(".googleAuthentication.session").length).to.equal(0)
  })

  it('should show Email login button', () => {
    return S2(".emailAuthentication.login").then(assertOneElementFound).then(done).catch(done)
  })

  it('should not show Email session', () => {
    expect(S(".emailAuthentication.session").length).to.equal(0)
  })

  it('should not show userDataForm', () => {
    expect(S("#userDataForm").length).to.equal(0)
  })

  it('should not show educationForm', () => {
    expect(S("#educationForm").length).to.equal(0)
  })

  it('should not show vetuma start', () => {
    expect(S(".vetumaStart").length).to.equal(0)
  })

  it('should not show hakuList', () => {
    expect(S(".hakuList").length).to.equal(0)
  })
})

describe('Page without session - invalid login token', () => {
  before(openPage("/hakuperusteet/#/token/nonExistingToken", hakuperusteetLoaded))

  it('should show login error message', () => {
    return S2(".authentication-error").then(assertOneElementFound).then(done).catch(done)
  })
})

describe('Page with email session - userdata', () => {
  before(openPage("/hakuperusteet/#/token/mochaTestToken", hakuperusteetLoaded))

  it('should show email as loggedIn user', () => {
    return S2(".loggedInAs").then(assertOneElementFound).then(done).catch(done)
  })

  it('should not show email login button', () => {
    expect(S(".emailAuthentication.login").length).to.equal(0)
  })

  it('should not show Google login button', () => {
    expect(S(".googleAuthentication.login").length).to.equal(0)
  })

  it('should not show Google session', () => {
    expect(S(".googleAuthentication.session").length).to.equal(0)
  })

  it('should show logout button', () => {
    return S2("#logout").then(assertOneElementFound).then(done).catch(done)
  })

  it('should show userDataForm', () => {
    return S2("#userDataForm").then(assertOneElementFound).then(done).catch(done)
  })

  it('should not show educationForm', () => {
    expect(S("#educationForm").length).to.equal(0)
  })

  it('should not show vetuma start', () => {
    expect(S(".vetumaStart").length).to.equal(0)
  })

  it('should not show hakuList', () => {
    expect(S(".hakuList").length).to.equal(0)
  })

  describe('Insert data', () => {
    it('initially submit should be disabled', assertSubmitDisabled)

    it('insert firstName', () => { S("#firstName").val("John").focus() })
    it('submit should be disabled', assertSubmitDisabled)

    it('insert lastName', () => { S("#lastName").val("Doe").focus() })
    it('submit should be disabled', assertSubmitDisabled)

    it('insert birthDate', () => { S("#birthDate").val("15051979").focus() })
    it('submit should be disabled', assertSubmitDisabled)

    it('select gender', () => { S("#gender-male").click() })
    it('submit should be disabled', assertSubmitDisabled)

    it('select nativeLanguage', () => { S("#nativeLanguage").val("FI").focus().blur() })
    it('submit should be disabled', assertSubmitDisabled)

    it('select nationality', () => { S("#nationality").val("246").focus().blur() })
    it('submit should be enabled', assertSubmitEnabled)

    it('select personId', () => { S("#hasPersonId").click() })
    it('submit should be disabled', assertSubmitDisabled)

    it('insert birthDate', () => { S("#personId").val("-9358").focus().blur() })
    it('submit should be enabled', assertSubmitEnabled)
  })

  describe('Submit userDataForm', () => {
    it('click submit should post userdata', () => {
      S("input[name='submit']").click()
      return S2("#educationForm").then(assertOneElementFound).then(done).catch(done)
    })
  })
})

describe('Page with email session - educationdata', () => {
  it('initially submit should be disabled', assertSubmitDisabled)

  it('select educationLevel', () => { S("#educationLevel").val("116").focus().blur() })
  it('submit should be disabled', assertSubmitDisabled)

  it('select educationCountry - Finland', () => { S("#educationCountry").val("246").focus().blur() })
  it('submit should be enabled', assertSubmitEnabled)
  it('noPaymentRequired should be visible', () => {
    return S2(".noPaymentRequired").then(assertOneElementFound).then(done).catch(done)
  })

  it('select educationCountry - Solomin Islands', () => { S("#educationCountry").val("090").focus().blur() })
  it('submit should be enabled', assertSubmitEnabled)
  it('noPaymentRequired should be visible', () => {
    return S2(".paymentRequired").then(assertOneElementFound).then(done).catch(done)
  })

  describe('Submit educationForm', () => {
    it('click submit should post educationdata', () => {
      S("input[name='submit']").click()
      return S2(".vetumaStart").then(assertOneElementFound).then(done).catch(done)
    })
  })
})

describe('Page with email session - vetuma start page', () => {
  // input name=submit is not allowed when doing redirect, hence different name than in other forms
  it('initially submit should be enabled', () => {
    return S2("input[name='submitVetuma']").then(expectToBeEnabled).then(done).catch(done)
  })

  describe('Submit vetumaForm', () => {
    it('click submit should go to vetuma and return back with successful payment', () => {
      S("input[name='submitVetuma']").click()
      return S2(".vetumaResult").then(assertOneElementFound).then(done).catch(done)
    })

    it('redirectForm should be visible', () => {
      return S2("#redirectToForm").then(assertOneElementFound).then(done).catch(done)
    })
  })
})

describe('Page with email session - hakulist page', () => {
  // reload page to get rid of cross domain errors when security is enabled in browser
  before(openPage("/hakuperusteet", hakuperusteetLoaded))

  it('initially submit should be enabled', () => {
    return S2("input[name='redirectToForm']").then(expectToBeEnabled).then(done).catch(done)
  })

  describe('Submit hakulist form', () => {
    it('click submit should redirect to form', () => {
      S("input[name='redirectToForm']").click()
      // todo: goto proper mock result form
    })
  })

  after(logout)
})

function assertOneElementFound(e) { expect(e.length).to.equal(1)}

function assertSubmitDisabled() { return S2("input[name='submit']").then(expectToBeDisabled).then(done).catch(done) }
function assertSubmitEnabled() { return S2("input[name='submit']").then(expectToBeEnabled).then(done).catch(done)}

function expectToBeDisabled(e) { expect($(e).attr("disabled")).to.equal("disabled") }
function expectToBeEnabled(e) { expect($(e).attr("disabled")).to.equal(undefined) }