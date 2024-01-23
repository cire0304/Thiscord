export function convertDateFormat(inputDate: string) { 
  let time = new Date(inputDate);
  time.setHours(time.getHours() + 9);
  return time.toLocaleString('ko-KR', { timeZone: 'Asia/Seoul' }) 
  
}

export function getCurrentTime() {
  return new Date().toJSON();
}
