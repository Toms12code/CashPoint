import { useState, useEffect } from 'react';
import api from '../api/axios';

function Categorias() {

    const [categorias, setCategorias] = useState([]);
    const [nombre, setNombre] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        cargarCategorias();
    }, []);

    const cargarCategorias = () => {
        api.get('/categorias')
            .then(response => {
                console.log(response.data)
                setCategorias(response.data);
            })
            .catch(() => setError('Error al cargar categorías'));
    };

    const crearCategoria = () => {
        if (!nombre.trim()) {
            setError('El nombre es obligatorio');
            return;
        }

        api.post('/categorias', { nombre, descripcion })
            .then(() => {
                setNombre('');
                setDescripcion('');
                setError('');
                cargarCategorias();
            })
            .catch(() => setError('Error al crear categoría'));
    };

    const eliminarCategoria = (id) => {
        api.delete(`/categorias/${id}`)
            .then(() => cargarCategorias())
            .catch(() => setError('Error al eliminar categoría'));
    };

    return (
        <div>
            <h1>Categorías</h1>

            {error && <p style={{ color: 'red' }}>{error}</p>}

            {/* Formulario */}
            <div style={{ marginBottom: '20px' }}>
                <input
                    placeholder="Nombre"
                    value={nombre}
                    onChange={e => setNombre(e.target.value)}
                    style={inputStyle}
                />
                <input
                    placeholder="Descripción"
                    value={descripcion}
                    onChange={e => setDescripcion(e.target.value)}
                    style={inputStyle}
                />
                <button onClick={crearCategoria} style={buttonStyle}>
                    Crear
                </button>
            </div>

            {/* Tabla */}
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                    <tr style={{ backgroundColor: '#1a1a2e', color: 'white' }}>
                        <th style={thStyle}>ID</th>
                        <th style={thStyle}>Nombre</th>
                        <th style={thStyle}>Descripción</th>
                        <th style={thStyle}>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {categorias.map(c => (
                        <tr key={c.id} style={{ borderBottom: '1px solid #ddd' }}>
                            <td style={tdStyle}>{c.id}</td>
                            <td style={tdStyle}>{c.nombre}</td>
                            <td style={tdStyle}>{c.descripcion}</td>
                            <td style={tdStyle}>
                                <button
                                    onClick={() => eliminarCategoria(c.id)}
                                    style={deleteButtonStyle}
                                >
                                    Eliminar
                                </button>
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

const thStyle = {
    padding: '10px',
    textAlign: 'left'
};

const tdStyle = {
    padding: '10px'
};

export default Categorias;