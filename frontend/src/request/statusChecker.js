export const checkStatus = response => {
    if(response.status >= 200 && response.status < 300) {
        return response
    }
    const error = new Error(response.statusText)
    error.response = response
    return Promise.reject(error)
}