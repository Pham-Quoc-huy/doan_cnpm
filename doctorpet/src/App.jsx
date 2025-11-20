import './App.css'
import Login from './pages/Login'
import Register from './pages/Register'
import AppRouter from './routes/Approuter'
import SupportLayout from './support/layouts/SupportLayout'
import ScheduleItem from './user/components/ScheduleItem'
import UserLayout from './user/layouts/UserLayout'
function App() {
  return <AppRouter/>
}
export default App
