function cookiesEnabled() {
  writeCookieValue("cookiesEnabled", "true");
  return readCookieValue("cookiesEnabled") == "true";
}

function readCookieValue(name) {
  if (document.cookie.length == 0)
    return null;
  begin = document.cookie.indexOf(name + "=");
  if (begin < 0)
    return null;
  begin += name.length + 1;
  end = document.cookie.indexOf(";", begin);
  if (end == -1)
    end = document.cookie.length;
  return document.cookie.substring(begin, end);
}

function writeCookieValue(name, value) {
  var ExpireDate = new Date();
  ExpireDate.setTime(ExpireDate.getTime() + (365 * 24 * 3600 * 1000));
  document.cookie = name + "=" + value + ";expires=" + ExpireDate.toGMTString();
}