/**
 * 
 * @param {*} fileName 
 * @returns 
 */
export function generateImageKey(fileName) {
    const MAX_ID_LENGTH = 7; 

    const randomAlphanumeric = () => Math.random().toString(36).substr(2, MAX_ID_LENGTH);
    return randomAlphanumeric() + "::" + fileName;
}