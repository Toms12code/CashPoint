import { useState, useEffect } from "react";
import api from "../api/axios";

function Ventas() {

    const [ventas, setVentas] = useState([]);
    const [productos, setProductos] = useState([]);
    const [detalles, setDetalles] = useState([]);
    const [productoId, setProductoId] = useState('');
    const [cantidad, setCantidad] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        cargarVentas();
        cargarProductos();
    }, []);

    const cargarVentas = () => {
        api.get('/ventas')
            .then(response => setVentas(response.data))
            .catch(() => setError('Error al cargar ventas'));
    };

    const cargarProductos = () => {
        api.get('/productos')
            .then(response => setProductos(response.data))
            .catch(() => setError('Error al cargar productos'));
    };

    const agregarDetalle = () => {
        if (!productoId || !cantidad) {
            setError('Selecciona un producto y cantidad');
            return;
        }

        const producto = productos.find(p => p.id === parseInt(productoId));

        const yaExiste = detalles.find(d => d.productoId === parseInt(productoId));
        if (yaExiste) {
            setError('Ese producto ya está en la venta');
            return;
        }

        setDetalles([...detalles, {
            productoId: parseInt(productoId),
            cantidad: parseInt(cantidad),
            productoNombre: producto.nombre,
            precioUnitario: producto.precio
        }]);

        setProductoId('');
        setCantidad('');
        setError('');
    };

    const quitarDetalle = (id) => {
        setDetalles(detalles.filter(d => d.productoId !== id));
    };

    const crearVenta = () => {
        if (detalles.length === 0) {
            setError('Agrega al menos un producto');
            return;
        }

        api.post('/ventas', {
            detalles: detalles.map(d => ({
                productoId: d.productoId,
                cantidad: d.cantidad
            }))
        })
            .then(() => {
                setDetalles([]);
                setError('');
                cargarVentas();
            })
            .catch(() => setError('Error al crear venta'));
    };

    const anularVenta = (id) => {
        api.put(`/ventas/${id}/anular`)
            .then(() => cargarVentas())
            .catch(() => setError('Error al anular venta'));
    };

    const calcularTotal = () => {
        return detalles.reduce((acc, d) => acc + (d.precioUnitario * d.cantidad), 0);
    };

    return (
        <div>
            <h1>Ventas</h1>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            <div style={{ marginBottom: '10px' }}>
                <select
                    value={productoId}
                    onChange={e => setProductoId(e.target.value)}
                    style={inputStyle}
                >
                    <option value="">Seleccionar producto</option>
                    {productos.map(p => (
                        <option key={p.id} value={p.id}>
                            {p.nombre} — ${p.precio}
                        </option>
                    ))}
                </select>
                <input
                    placeholder="Cantidad"
                    type="number"
                    value={cantidad}
                    onChange={e => setCantidad(e.target.value)}
                    style={inputStyle}
                />
                <button onClick={agregarDetalle} style={buttonStyle}>
                    Agregar
                </button>
            </div>

            {detalles.length > 0 && (
                <div style={{ marginBottom: '20px', border: '1px solid #ddd', padding: '15px', borderRadius: '4px' }}>
                    <h3>Venta actual</h3>
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                            <tr style={{ backgroundColor: '#1a1a2e', color: 'white' }}>
                                <th style={thStyle}>Producto</th>
                                <th style={thStyle}>Cantidad</th>
                                <th style={thStyle}>Precio unitario</th>
                                <th style={thStyle}>Subtotal</th>
                                <th style={thStyle}></th>
                            </tr>
                        </thead>
                        <tbody>
                            {detalles.map(d => (
                                <tr key={d.productoId} style={{ borderBottom: '1px solid #ddd' }}>
                                    <td style={tdStyle}>{d.productoNombre}</td>
                                    <td style={tdStyle}>{d.cantidad}</td>
                                    <td style={tdStyle}>${d.precioUnitario}</td>
                                    <td style={tdStyle}>${d.precioUnitario * d.cantidad}</td>
                                    <td style={tdStyle}>
                                        <button onClick={() => quitarDetalle(d.productoId)} style={deleteButtonStyle}>
                                            Quitar
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <h3>Total: ${calcularTotal()}</h3>
                    <button onClick={crearVenta} style={buttonStyle}>
                        Confirmar venta
                    </button>
                </div>
            )}

            <h2>Historial</h2>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr style={{ backgroundColor: '#1a1a2e', color: 'white' }}>
                        <th style={thStyle}>ID</th>
                        <th style={thStyle}>Fecha</th>
                        <th style={thStyle}>Total</th>
                        <th style={thStyle}>Estado</th>
                        <th style={thStyle}>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {ventas.map(v => (
                        <tr key={v.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td style={tdStyle}>{v.id}</td>
                            <td style={tdStyle}>{new Date(v.fecha).toLocaleString()}</td>
                            <td style={tdStyle}>${v.total}</td>
                            <td style={tdStyle}>{v.estado}</td>
                            <td style={tdStyle}>
                                {v.estado === 'ACTIVA' && (
                                    <button onClick={() => anularVenta(v.id)} style={deleteButtonStyle}>
                                        Anular
                                    </button>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

const inputStyle = {
    padding: '8px',
    marginRight: '10px',
    borderRadius: '4px',
    border: '1px solid #ccc',
    fontSize: '14px'
};

const buttonStyle = {
    padding: '8px 16px',
    backgroundColor: '#1a1a2e',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer'
};

const deleteButtonStyle = {
    ...buttonStyle,
    backgroundColor: '#c0392b'
};

const thStyle = { padding: '10px', textAlign: 'left' };
const tdStyle = { padding: '10px' };

export default Ventas;