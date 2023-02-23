import Header from "../components/Header"
import ChannelsList from "../components/ChannelsList"
import Chat from "../components/Chat"

export default function Home() {
    return (
        <div>
            <Header />
            <div className="container-fluid">
            <div className="row">
                <ChannelsList />
                <Chat />
            </div>
            </div>
      </div>
    )
}