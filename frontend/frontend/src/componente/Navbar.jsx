import { Link, useLocation } from 'react-router-dom';
import { MdDashboard, MdInventory, MdCategory, MdPointOfSale } from 'react-icons/md';
import { FaBoxes } from 'react-icons/fa';

function Navbar() {

    const location = useLocation();

    const links = [
        { to: '/', label: 'Dashboard', icon: <MdDashboard /> },
        { to: '/categorias', label: 'Categorías', icon: <MdCategory /> },
        { to: '/producto', label: 'Productos', icon: <FaBoxes /> },
        { to: '/ventas', label: 'Ventas', icon: <MdPointOfSale /> },
        { to: '/inventario', label: 'Inventario', icon: <MdInventory /> },
    ];

    return (
        <nav style={navStyle}>
            <span style={logoStyle}>CashPoint</span>
            <div style={{ display: 'flex', gap: '8px' }}>
                {links.map(link => (
                    <Link
                        key={link.to}
                        to={link.to}
                        style={{
                            ...linkStyle,
                            backgroundColor: location.pathname === link.to ? '#4f46e5' : 'transparent'
                        }}
                    >
                        <span style={{ fontSize: '18px' }}>{link.icon}</span>
                        {link.label}
                    </Link>
                ))}
            </div>
        </nav>
    );
}

const navStyle = {
    backgroundColor: '#13132b',
    padding: '14px 30px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    borderBottom: '1px solid #2e2e5e',
    position: 'sticky',
    top: 0,
    zIndex: 100
};

const logoStyle = {
    color: '#4f46e5',
    fontWeight: '800',
    fontSize: '20px',
    letterSpacing: '1px'
};

const linkStyle = {
    color: '#e0e0e0',
    textDecoration: 'none',
    padding: '8px 16px',
    borderRadius: '8px',
    display: 'flex',
    alignItems: 'center',
    gap: '8px',
    fontSize: '14px',
    fontWeight: '500',
    transition: 'background-color 0.2s'
};

export default Navbar;