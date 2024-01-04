const isEmailFormat = (email: string) => {
  const emailRegex =
    /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
  return emailRegex.test(email);
};

const isEmpty = (str: string) => {
  return !str || 0 === str.length;
}

const Utils = {
    isEmailFormat,
    isEmpty
}

export default Utils;
