export function convertDateFormat(inputDate: any) {
    const dateObject = new Date(inputDate);
    
    const year = dateObject.getFullYear();
    const month = (dateObject.getMonth() + 1).toString().padStart(2, '0');
    const day = dateObject.getDate().toString().padStart(2, '0');
    const hours =  parseInt(dateObject.getHours().toString().padStart(2, '0'));
    const minutes = dateObject.getMinutes().toString().padStart(2, '0');
    const ampm = hours < 12 ? '오전' : '오후';
    const formattedHours = hours % 12 || 12;
  
    const formattedDate = `${year}.${month}.${day}. ${ampm} ${formattedHours}:${minutes}`;
  
    return formattedDate;
  }


export function getCurrentTime() {
    return new Date().toLocaleString('en-US', {
        timeZone: 'Asia/Seoul',
      });
}
