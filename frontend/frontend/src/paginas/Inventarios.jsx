import { useState, useEffect } from "react";
import api from "../api/axios";

function Inventarios() {
    const [productos, setProductos] = useState([]);
    const [historial, setHistorial] = useState([]);
    const [productoId, setProductoId] = useState('');
    const [cantidad, setCantidad] = useState('');
    const [motivo, setMotivo] = useState('');
    const [error, setError] = useState('');



    useEffect(() => {
        cargarProductos();
    }, []);

    const cargarProductos = () => {
        api.get('/productos')
            .then(response => setProductos(response.data))
            .catch(error => {
                setError('Paila, error al cargar los productos');
            });
    };

    const cargarHistorial = (id) => {
        api.get (`/inventarios/historial/${id}`)
            .then(response => setHistorial(response.data))
            .catch(() => setError('Error al cargar el historial'));
    };

    const registrarEntrada = () => {
        if (!productoId || !cantidad ) {
            setError('Selecciona un producto y cantidad');
            return;
        }

        api.post('/inventarios/entrada', {
            productoId: parseInt(productoId),
            cantidad: parseInt(cantidad),
            motivo
        })
          .then (() => {
            cargarProductos();
            cargarHistorial(productoId);
            setCantidad('');
            setMotivo('');
            setError(''); 
          })

            .catch(() => setError('Error al registrar la entrada brou'));
    
    
    };

    const registrarSalida = () => {
        if (!productoId || !cantidad ) {
            setError('Selecciona un producto y cantidad');
            return;
        }

        api.post('/inventarios/salida', {
            productoId: parseInt(productoId),
            cantidad: parseInt(cantidad),
            motivo
        })
          .then (() => {
            cargarProductos();
            cargarHistorial(productoId);
            setCantidad('');
            setMotivo('');
            setError('');
          })
          .catch(() => setError('Error al registrar la salida brou'));
    };

    const handleProductoChange =(e) => {
        setProductoId(e.target.value);
        if (e.target.value) {
            cargarHistorial(e.target.value);
        } else {
            setHistorial([]);
        }
    };

    return (
        <div>
            <h1>Inventario</h1>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            <div style={{ marginBottom: '20px' }}>
                <select
                    value={productoId}
                    onChange={handleProductoChange}
                    style={inputStyle}
                >
                    <option value="">Seleccionar producto</option>
                    {productos.map(p => (
                        <option key={p.id} value={p.id}>
                            {p.nombre} — Stock: {p.stock}
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
                <input
                    placeholder="Motivo"
                    value={motivo}
                    onChange={e => setMotivo(e.target.value)}
                    style={inputStyle}
                />
                <button onClick={registrarEntrada} style={buttonStyle}>
                    Entrada
                </button>
                <button onClick={registrarSalida} style={{ ...buttonStyle, backgroundColor: '#c0392b', marginLeft: '10px' }}>
                    Salida
                </button>
            </div>

            {historial.length > 0 && (
                <>
                    <h2>Historial del producto</h2>
                    <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                        <thead>
                            <tr style={{ backgroundColor: '#1a1a2e', color: 'white' }}>
                                <th style={thStyle}>Fecha</th>
                                <th style={thStyle}>Tipo</th>
                                <th style={thStyle}>Cantidad</th>
                                <th style={thStyle}>Motivo</th>
                            </tr>
                        </thead>
                        <tbody>
                            {historial.map(m => (
                                <tr key={m.id} style={{ borderBottom: '1px solid #ddd' }}>
                                    <td style={tdStyle}>{new Date(m.fecha).toLocaleString()}</td>
                                    <td style={tdStyle}>{m.tipo}</td>
                                    <td style={tdStyle}>{m.cantidad}</td>
                                    <td style={tdStyle}>{m.motivo}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </>
            )}
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

const thStyle = { padding: '10px', textAlign: 'left' };
const tdStyle = { padding: '10px' };

export default Inventarios;
    
