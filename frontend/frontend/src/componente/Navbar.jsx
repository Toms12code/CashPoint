import { Link } from "react-router-dom";

function Navbar() {
    return (
        <nav style={{
            backgroundColor: '#000000',
            padding: '10px',
            display: 'flex',
            gap: '20px',
        }}>
            <Link to="/categorias" style={estiloLink}>Categorías</Link>
            <Link to="/inventario" style={estiloLink}>Inventario</Link>
            <Link to="/producto" style={estiloLink}>Productos</Link>
            <Link to="/ventas" style={estiloLink}>Ventas</Link>
        </nav>
    );
}   

const estiloLink = {
    color: 'skyblue',
    textDecoration: 'none',
    fontSize: '16px',
    fontWeight: 'bold',
};

export default Navbar;