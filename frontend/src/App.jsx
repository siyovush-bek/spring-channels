import Header from "./components/Header"
import ChannelsList from "./components/ChannelsList"
import Chat from "./components/Chat"
import './App.css'


function App() {

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

export default App
