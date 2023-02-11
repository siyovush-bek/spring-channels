import React from "react";
import ChannelHeader from "./ChannelHeader";
import Message from "./Message";


export default function MessageBox(){
    return (
        <div>
            <ChannelHeader />
            <div className="pt-5 pr-5">
            <Message 
                current_user={true}
                sent_at="2:33 am"
                body={"Hi! What's up!"}
                sender={"JJ"} 
            />
            </div>
        </div>
    )
}