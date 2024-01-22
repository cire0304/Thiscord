import notifiaciton from "../assets/sounds/notification.mp3";

class Alarm {

    private readonly audio: any;    
    
    constructor(auido: any) {
        this.audio = new Audio(auido);
    }

    public play() {
        this.audio.play();
    }

}

export const Notify = new Alarm(notifiaciton);
