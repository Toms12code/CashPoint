import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import Navbar from './componente/Navbar';
import Categorias from './paginas/Categorias';
import Inventarios from './paginas/Inventarios';
import Productos from './paginas/Productos';
import Ventas from './paginas/Ventas';
import Dashboard from './paginas/Dashboard';

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <div >
        <Routes>
          <Route path="/categorias" element={<Categorias />} />
          <Route path="/inventario" element={<Inventarios />} />
          <Route path="/producto" element={<Productos />} />
          <Route path="/ventas" element={<Ventas />} />
          <Route path="/" element={<Dashboard />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
} 


export default App;