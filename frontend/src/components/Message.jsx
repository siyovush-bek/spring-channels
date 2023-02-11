import React from "react";


export default function Message(props) {
    return (
        <div>
            <div className={`chat-message-${props.current_user ? "right" : "left"} pb-4`}>
                <div className="flex-shrink-1 bg-light rounded py-2 px-3 ml-3">
                    <div className="font-weight-bold mb-1">{props.sender}</div>
                    {props.body}
                    <div className="text-muted small text-nowrap mt-2">{props.sent_at}</div>
                </div>
                <div> 
                    
                </div>
            </div>
        </div>
    )
}