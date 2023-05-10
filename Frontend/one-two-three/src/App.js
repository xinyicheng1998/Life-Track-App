import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Activity from './pages/Activity';
import Place from './pages/Place';
import Stay from './pages/Stay';

function App() {
  return (
    <Routes>
      <Route path='/' element={<Home />} />
      <Route path='/activity' element={<Activity />} />
      <Route path='/place' element={<Place />} />
      <Route path='/stay' element={<Stay />} />
    </Routes>
  );
}

export default App;
