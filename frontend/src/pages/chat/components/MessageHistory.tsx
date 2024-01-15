import React from 'react'
import { ChatMessageHitory } from '../../../api/ChatAPI'
import ProfileImage from '../../../components/profileImage'

function MessageHistory({chatHistories}: {chatHistories?:ChatMessageHitory[]} ) {
  return (
    <div>
    {chatHistories &&
        chatHistories.map((chat, index) => (
          <div style={{
            display: "flex",
            alignItems: "center",
            padding: "10px",
            width: "100%",
            boxSizing: "border-box",
            border: "1px solid black"
          }} key={index}>
            <ProfileImage
              src={`https://gravatar.com/avatar/${chat.user.id}?d=identicon`}
              size="30px"/>
              <div style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "flex-start",
                alignItems: "flex-start",
                padding: "0 10px",
                width: "100%",
                boxSizing: "border-box"
              }}>
                <p style={{margin: "0px",}} >{`${chat.user.nickname}#${chat.message.sentDateTime}`}</p>
                {chat.message.content}
        
              </div>
            {/* <p key={index}>
              {chat.user.nickname} : {chat.message.content}
            </p> */}
          </div>
        ))}
        </div>
  )
}

export default MessageHistory

