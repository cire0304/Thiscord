import React, { useEffect } from "react";

function AudioAccess({ children }: { children: React.ReactNode }) {
  useEffect(() => {
    var AudioContext;
    var audioContext;

    window.onload = function () {
      navigator.mediaDevices
        .getUserMedia({ audio: true })
        .then(() => {
          AudioContext = window.AudioContext || window.AudioContext;
          audioContext = new AudioContext();
        })
        .catch((e) => {
          console.error(`Audio permissions denied: ${e}`);
        });
    };
  }, []);

  return <>{children}</>;
}

export default AudioAccess;
