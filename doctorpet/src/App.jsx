import './App.css'
import Home from './pages/Home'
import Login from './pages/Login'
import Register from './pages/Register'
import RegisterVet from './pages/RegisterVet'
import AppRouter from './routes/Approuter'
import SupportLayout from './support/layouts/SupportLayout'
import ScheduleItem from './user/components/ScheduleItem'
import UserLayout from './user/layouts/UserLayout'
import Support from './support/routes/App'
function App() {
  return <AppRouter />
}
export default App
