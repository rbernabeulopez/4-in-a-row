import './Tile.css';

export const Tile = (props) => {
    return(
    <td className="border">
    <span className="tile" id={props.value} onClick={props.handleClick} onMouseEnter={props.handleMouseEnter} onMouseLeave={props.handleMouseLeave} >
        <input type="hidden" value={props.value} />
    </span>
</td>
); 
}