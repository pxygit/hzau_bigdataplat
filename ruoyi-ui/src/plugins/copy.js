export default {
  text(text) {
    const el = document.createElement('textarea')
    el.value = text
    document.body.appendChild(el)
    el.select()
    const successful = document.execCommand('copy')
    document.body.removeChild(el)
    return successful
  }
}
