
const AccessTokenKey = 'ACCESS_TOKEN'

// ========== Token 相关 ==========

export function getAccessToken() {
  return localStorage.getItem(AccessTokenKey)
}


export function setToken(token) {
  localStorage.setItem(AccessTokenKey, token)
}

export function removeToken() {
  localStorage.removeItem(AccessTokenKey)
}