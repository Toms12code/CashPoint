import { useState, useEffect } from 'react';
import { MdPointOfSale, MdInventory, MdTrendingUp, MdWarning } from 'react-icons/md';
import { FaBoxes } from 'react-icons/fa';
import api from '../api/axios';

function Dashboard() {

    const [ventas, setVentas] = useState([]);
    const [productos, setProductos] = useState([]);

    useEffect(() => {
        api.get('/ventas').then(r => setVentas(r.data));
        api.get('/productos').then(r => setProductos(r.data));
    }, []);

    const ventasActivas = ventas.filter(v => v.estado === 'ACTIVA');
    const totalVentas = ventasActivas.reduce((acc, v) => acc + v.total, 0);
    const stockBajo = productos.filter(p => p.stock < 10);
    const ultimasVentas = [...ventas].reverse().slice(0, 5);

    const maxTotal = Math.max(...ventasActivas.slice(-7).map(v => v.total), 1);

    return (
        <div style={pageStyle}>

            {/* Header */}
            <div style={headerStyle}>
                <div>
                    <h1 style={{ margin: 0, fontSize: '26px' }}>Bienvenido 👋</h1>
                    <p style={{ color: '#6b6b9a', marginTop: '4px', fontSize: '14px' }}>
                        {new Date().toLocaleDateString('es-CO', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}
                    </p>
                </div>
            </div>

            {/* Cards */}
            <div style={cardsGrid}>
                <StatCard icon={<MdPointOfSale size={24} />} label="Ventas activas" value={ventasActivas.length} color="#4f46e5" />
                <StatCard icon={<MdTrendingUp size={24} />} label="Total en ventas" value={`$${totalVentas.toLocaleString()}`} color="#10b981" />
                <StatCard icon={<FaBoxes size={24} />} label="Productos" value={productos.length} color="#f59e0b" />
                <StatCard icon={<MdWarning size={24} />} label="Stock bajo" value={stockBajo.length} color="#ef4444" />
            </div>

            {/* Fila del medio */}
            <div style={midGrid}>

                {/* Gráfica */}
                <div style={panelStyle}>
                    <p style={panelTitle}>📊 Últimas ventas</p>
                    <div style={{ display: 'flex', alignItems: 'flex-end', gap: '10px', height: '180px', marginTop: '16px' }}>
                        {ventasActivas.slice(-7).map((v, i) => {
                            const altura = (v.total / maxTotal) * 160;
                            return (
                                <div key={i} style={{ flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                                    <span style={{ color: '#e0e0e0', fontSize: '10px', marginBottom: '4px' }}>
                                        ${v.total.toLocaleString()}
                                    </span>
                                    <div style={{
                                        width: '100%',
                                        height: `${altura}px`,
                                        background: 'linear-gradient(180deg, #6366f1, #4f46e5)',
                                        borderRadius: '6px 6px 0 0'
                                    }} />
                                    <span style={{ color: '#6b6b9a', fontSize: '10px', marginTop: '4px' }}>#{v.id}</span>
                                </div>
                            );
                        })}
                    </div>
                </div>

                {/* Stock bajo */}
                <div style={panelStyle}>
                    <p style={panelTitle}>⚠️ Stock bajo</p>
                    {stockBajo.length === 0
                        ? (
                            <div style={emptyStyle}>
                                <span style={{ fontSize: '32px' }}>✅</span>
                                <p style={{ color: '#6b6b9a', marginTop: '8px', fontSize: '13px' }}>Todo el stock está bien</p>
                            </div>
                        )
                        : stockBajo.map(p => (
                            <div key={p.id} style={stockItemStyle}>
                                <span style={{ fontSize: '14px' }}>{p.nombre}</span>
                                <span style={{
                                    backgroundColor: p.stock === 0 ? '#ef4444' : '#f59e0b',
                                    color: 'white',
                                    padding: '2px 10px',
                                    borderRadius: '20px',
                                    fontSize: '12px',
                                    fontWeight: '700'
                                }}>
                                    {p.stock === 0 ? 'Sin stock' : `${p.stock} uds`}
                                </span>
                            </div>
                        ))
                    }
                </div>
            </div>

            {/* Últimas ventas */}
            <div style={panelStyle}>
                <p style={panelTitle}>🧾 Historial reciente</p>
                <table style={{ width: '100%', marginTop: '16px' }}>
                    <thead>
                        <tr style={{ color: '#6b6b9a', fontSize: '12px', textTransform: 'uppercase', letterSpacing: '0.5px' }}>
                            <th style={thStyle}>ID</th>
                            <th style={thStyle}>Fecha</th>
                            <th style={thStyle}>Total</th>
                            <th style={thStyle}>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        {ultimasVentas.map(v => (
                            <tr key={v.id} style={{ borderBottom: '1px solid #2e2e5e' }}>
                                <td style={tdStyle}>
                                    <span style={{ color: '#4f46e5', fontWeight: '700' }}>#{v.id}</span>
                                </td>
                                <td style={tdStyle}>{new Date(v.fecha).toLocaleString('es-CO')}</td>
                                <td style={{ ...tdStyle, fontWeight: '700', color: '#10b981' }}>
                                    ${v.total.toLocaleString()}
                                </td>
                                <td style={tdStyle}>
                                    <span style={{
                                        backgroundColor: v.estado === 'ACTIVA' ? '#10b98120' : '#ef444420',
                                        color: v.estado === 'ACTIVA' ? '#10b981' : '#ef4444',
                                        padding: '4px 12px',
                                        borderRadius: '20px',
                                        fontSize: '12px',
                                        fontWeight: '600'
                                    }}>
                                        {v.estado}
                                    </span>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

function StatCard({ icon, label, value, color }) {
    return (
        <div style={cardStyle}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                <div>
                    <p style={{ color: '#6b6b9a', fontSize: '13px', marginBottom: '8px' }}>{label}</p>
                    <p style={{ fontSize: '30px', fontWeight: '800', color: '#ffffff' }}>{value}</p>
                </div>
                <div style={{
                    backgroundColor: `${color}20`,
                    color,
                    padding: '12px',
                    borderRadius: '12px'
                }}>
                    {icon}
                </div>
            </div>
            <div style={{ height: '3px', backgroundColor: color, borderRadius: '2px', marginTop: '16px' }} />
        </div>
    );
}

const pageStyle = {
    padding: '30px',
    maxWidth: '1200px',
    margin: '0 auto'
};

const headerStyle = {
    marginBottom: '28px'
};

const cardsGrid = {
    display: 'grid',
    gridTemplateColumns: 'repeat(4, 1fr)',                  
    gap: '16px',
    marginBottom: '20px'
};

const midGrid = {
    display: 'grid',
    gridTemplateColumns: '2fr 1fr',
    gap: '16px',
    marginBottom: '20px'
};

const panelStyle = {
    backgroundColor: '#13132b',
    border: '1px solid #2e2e5e',
    borderRadius: '16px',
    padding: '24px'
};

const cardStyle = {
    backgroundColor: '#13132b',
    border: '1px solid #2e2e5e',
    borderRadius: '16px',
    padding: '24px'
};

const panelTitle = {
    fontSize: '15px',
    fontWeight: '700',
    color: '#ffffff',
    margin: 0
};

const emptyStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '120px'
};

const stockItemStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '10px 0',
    borderBottom: '1px solid #2e2e5e',
    color: '#e0e0e0'
};

const thStyle = { padding: '10px', textAlign: 'left', fontWeight: '500' };
const tdStyle = { padding: '12px 10px', fontSize: '14px' , color: '#e0e0e0'};

export default Dashboard;