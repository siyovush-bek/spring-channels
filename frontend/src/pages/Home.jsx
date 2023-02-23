import Header from "../components/Header"
import ChannelsList from "../components/ChannelsList"
import Channel from "../components/Channel"
import { useParams } from "react-router-dom"

export default function Home() {
    const {channelName} = useParams()
    return (
        <div>
            <Header />
            <div className="container-fluid">
                <div className="row">
                    <ChannelsList />
                    <main role="main" className="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                        <Channel name={channelName} />
                    </main>
                </div>
            </div>
      </div>
    )
}