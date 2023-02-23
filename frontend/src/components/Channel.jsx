import React from "react";
import Message from "./Message";

export default function Channel(props){
    return (
        <div>
            {/* Channel Header */}
            <div className="py-2 px-4 border-bottom d-none d-lg-block">
                <h2>{props.name}</h2>
            </div>

            {/* Messages Block */}
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