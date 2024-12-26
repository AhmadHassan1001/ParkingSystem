import {
  Route,
  BrowserRouter as Router,
  Routes,
} from "react-router-dom";
import './App.css';
import ParkingLotsList from "./ParkingLotsList/ParkingLotsList";

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<ParkingLotsList/>}/>
      </Routes>
    </Router>
  );
}

export default App;
