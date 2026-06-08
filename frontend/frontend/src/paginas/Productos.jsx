import { useState, useEffect } from 'react';
import api from '../api/axios';

function Productos() {

    const [productos, setProductos] = useState([]);
    const [categorias, setCategorias] = useState([]);
    const [nombre, setNombre] = useState('');
    const [precio, setPrecio] = useState('');
    const [stock, setStock] = useState('');
    const [categoriaId, setCategoriaId] = useState('');
    const [error, setError] = useState('');
    const [confirmarId, setConfirmarId] = useState(null);

    useEffect(() => {
        cargarProductos();
        cargarCategorias();
    }, []);

    const cargarProductos = () => {
        api.get('/productos')
            .then(response => setProductos(response.data))
            .catch(() => setError('Error al cargar productos'));
    };

    const cargarCategorias = () => {
        api.get('/categorias')
            .then(response => setCategorias(response.data))
            .catch(() => setError('Error al cargar categorías'));
    };

    const crearProducto = () => {
        if (!nombre.trim() || !precio || !stock || !categoriaId) {
            setError('Todos los campos son obligatorios');
            return;
        }
        
        if(parseFloat(precio) < 50) {
            setError('El precio debe ser mayor o igual a $50');
            return;
        }

        if(parseInt(stock) < 1) {
            setError('El stock debe ser un número positivo');
            return;
        }

        api.post('/productos', {
            nombre,
            precio: parseFloat(precio),
            stock: parseInt(stock),
            categoriaId: parseInt(categoriaId)
        })
            .then(() => {
                setNombre('');
                setPrecio('');
                setStock('');
                setCategoriaId('');
                setError('');
                cargarProductos();
            })
            .catch(() => setError('Error al crear producto'));
    };

    const eliminarProducto = (id) => {
        api.delete(`/productos/${id}`)
            .then(() => cargarProductos())
            .catch(() => setError('Error al eliminar producto'));
    };

    return (
        <div>
            <h1>Productos</h1>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            <div style={{ marginBottom: '20px' }}>
                <input
                    placeholder="Nombre"
                    value={nombre}
                    onChange={e => setNombre(e.target.value)}
                    style={inputStyle}
                />
                <input
                    placeholder="Precio"
                    type="number"
                    min="50"
                    value={precio}
                    onChange={e => setPrecio(e.target.value)}
                    style={inputStyle}
                />
                <input
                    placeholder="Stock"
                    type="number"
                    min="1"
                    value={stock}
                    onChange={e => setStock(e.target.value)}
                    style={inputStyle}
                />
                <select
                    value={categoriaId}
                    onChange={e => setCategoriaId(e.target.value)}
                    style={inputStyle}
                >
                    <option value="">Seleccionar categoría</option>
                    {categorias.map(c => (
                        <option key={c.id} value={c.id}>{c.nombre}</option>
                    ))}
                </select>
                <button onClick={crearProducto} style={buttonStyle}>
                    Crear
                </button>
            </div>

            {/* Tabla */}
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr style={{ backgroundColor: '#1a1a2e', color: 'white' }}>
                        <th style={thStyle}>ID</th>
                        <th style={thStyle}>Nombre</th>
                        <th style={thStyle}>Precio</th>
                        <th style={thStyle}>Stock</th>
                        <th style={thStyle}>Categoría</th>
                        <th style={thStyle}>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {productos.map(p => (
                        <tr key={p.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td style={tdStyle}>{p.id}</td>
                            <td style={tdStyle}>{p.nombre}</td>
                            <td style={tdStyle}>${p.precio}</td>
                            <td style={tdStyle}>{p.stock}</td>
                            <td style={tdStyle}>{p.categoriaNombre}</td>
                            <td style={tdStyle}>
                            <td style={tdStyle}>
                                    {confirmarId === p.id ? (
                    <>
            <span style={{ fontSize: '13px', marginRight: '8px', color: '#000000' }}>
                ¿Seguro?
            </span>
            <button
                onClick={() => {
                    eliminarProducto(p.id);
                    setConfirmarId(null);
                }}
                style={{ ...deleteButtonStyle, marginRight: '6px' }}
            >
                Sí
            </button>
            <button
                onClick={() => setConfirmarId(null)}
                style={{ ...buttonStyle, backgroundColor: '#2e2e5e' }}
            >
                No
            </button>
        </>
    ) : (
        <button
            onClick={() => setConfirmarId(p.id)}
            style={deleteButtonStyle}
        >
            Eliminar
        </button>
    )}
</td>
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

export default Productos;