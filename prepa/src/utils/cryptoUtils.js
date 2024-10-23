// cryptoUtils.js

import CryptoJS from 'crypto-js';

const ENCRYPTION_KEY = '0ph4m'; // Remplacez par votre clé de chiffrement
const ENCRYPTION_IV = 'your-encryption-iv';   // Remplacez par votre vecteur d'initialisation

export const encryptPassword = (password) => {
  const key = CryptoJS.enc.Utf8.parse(ENCRYPTION_KEY);
  const iv = CryptoJS.enc.Utf8.parse(ENCRYPTION_IV);
  //const encryptedPassword = CryptoJS.AES.encrypt(password, key, { iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
  const encryptedPassword = CryptoJS.AES.encrypt(password,ENCRYPTION_KEY);
  return encryptedPassword.toString();
};

export const decryptPassword = (encryptedPassword) => {
  const key = CryptoJS.enc.Utf8.parse(ENCRYPTION_KEY);
  const iv = CryptoJS.enc.Utf8.parse(ENCRYPTION_IV);
  const decryptedBytes = CryptoJS.AES.decrypt(encryptedPassword, key, { iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
  return decryptedBytes.toString(CryptoJS.enc.Utf8);
};
