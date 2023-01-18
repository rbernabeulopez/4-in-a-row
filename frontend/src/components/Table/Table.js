import "bootstrap/dist/css/bootstrap.min.css"
import bootstrap from "bootstrap/dist/js/bootstrap"
import "./Table.css"
import { Row } from "../Row/Row"
import { useEffect, useState } from "react"

export const Table =() => {
	const [board, setBoard] = useState(Array(42).fill(""))
	const [turn, setTurn] = useState("Blue")
	const [resultModal, setResultModal] = useState(null)

	useEffect(() => {
		setResultModal(
			new bootstrap.Modal(document.getElementById("resultModal"), { keyboard: false, backdrop: "static" })
		)
		new bootstrap.Popover(document.querySelector('[data-bs-toggle="popover"]'), { container: "body", html: true })
    	}, [])
	
        const restart = () => window.location.reload()
	const animateDrop = (row, column) => {
		for (let i = 0; i < row; ++i) {
			document.getElementById(i * 7 + column).animate(
				[
					{
						opacity: 0,
						background:
							turn === "Blue"
								? "radial-gradient(circle at 2rem 2rem, dodgerblue, #333)"
								: "radial-gradient(circle at 2rem 2rem, crimson, #333)",
					},
					{
						opacity: 1,
						background:
							turn === "Blue"
								? "radial-gradient(circle at 2rem 2rem, dodgerblue, #333)"
								: "radial-gradient(circle at 2rem 2rem, crimson, #333)",
					},
				],
				{
					duration: 100,
					delay: i * 100,
				}
			)
		}
		setTimeout(() => document.getElementById(row * 7 + column).classList.add(turn), row * 100)
	}
	const handleMouseEnter = e => {
		const column = e.target.querySelector("input").value % 7
		document.getElementById("arrow" + column).className = turn === "Blue" ? "bg-primary" : "bg-danger"
	}
	const handleMouseLeave = e => {
		const column = e.target.querySelector("input").value % 7
		document.getElementById("arrow" + column).className = ""
	}
	const handleClick = e => {
		const tile = e.target.querySelector("input").value
		const position = getLowestPlace(tile)
		let _board = [...board]
		if (position !== false) {
			_board[position] = turn === "Blue" ? "Blue" : "Red"
			const column = position % 7
			const row = (position - column) / 7
			animateDrop(row, column)
			setBoard(_board)
			const result = checkWin(position)
			switch (result) {
				case 1:
					resultModal.show()
					break
				case 2:
					restart()
					break
				default:
					setTurn(_turn => (turn === "Blue" ? "Red" : "Blue"))
			}
		}
	}
	const getLowestPlace = current => {
		const column = current % 7
		let row = 5
		while (row >= 0) {
			if (board[row * 7 + column] === "") {
				return row * 7 + column
			}
			--row
		}
		return false
	}
	const checkWin = current => {
		if (!board.includes("")) return 2 // If board is fully filled
		const column = current % 7
		const row = (current - column) / 7
		let nw = Math.min(column, row)
		let ne = Math.min(6 - column, row)
		let count = 0
		// Horizontal
		for (let temp_column = 0; temp_column < 7; ++temp_column) {
			if (board[row * 7 + temp_column] === turn) {
				if (++count > 2) return 1
			} else {
				count = 0
			}
		}
		count = 0
		// Vertical
		for (let temp_row = 0; temp_row < 6; ++temp_row) {
			if (board[temp_row * 7 + column] === turn) {
				if (++count > 2) return 1
			} else {
				count = 0
			}
		}
		count = 0
		// L2R Diagonal
		for (
			let temp_row = row - nw, temp_column = column - nw;
			temp_row < 5 || temp_column < 6;
			++temp_row, ++temp_column
		) {
			if (board[temp_row * 7 + temp_column] === turn) {
				if (++count > 2) return 1
			} else {
				count = 0
			}
		}
		count = 0
		// R2l Diagonal
		for (
			let temp_row = row - ne, temp_column = column + ne;
			temp_row < 5 || temp_column > 0;
			++temp_row, --temp_column
		) {
			if (board[temp_row * 7 + temp_column] === turn) {
				if (++count > 2) return 1
			} else {
				count = 0
			}
		}
		count = 0
		return 0
	}
	return (
		<main> 
			<div
				className="modal fade"
				id="resultModal"
				tabIndex="-1"
				aria-labelledby="resultModalLabel"
				aria-hidden="true">
				<div className="modal-dialog modal-dialog-centered">
					<div className="modal-content">
						<div className="modal-header">
							<h3 className="modal-title" id="resultModalLabel">
								Game Over
							</h3>
						</div>
						<div className="modal-body">
							<h5>
								<span className={turn === "Blue" ? "text-primary" : "text-danger"}>{turn}</span> has won
								the game!
							</h5>
						</div>
						<div className="modal-footer">
							<button type="button" className="btn btn-secondary" onClick={window.close}>
								Exit
							</button>
							<button type="button" className="btn btn-primary" onClick={restart}>
								Restart
							</button>
						</div>
					</div>
				</div>
			</div> 
			<div className="card shadow-lg text-center p-3">
				<div className="d-flex justify-content-between">
					<h1 className={turn === "Blue" ? "text-primary" : "text-danger"}>4 in a row</h1>
					<button
						type="button"
						className="btn btn-warning fs-5"
						data-bs-container="body"
						data-bs-toggle="popover"
						data-bs-placement="bottom"
						data-bs-content="<ul>
							<li>Firstly, decide who goes <i>first</i> and what <b>color</b> each player will have.</li>
							<li>Players must take <i>alternate turns</i>, and only <b>one disc</b> can be dropped in <b>each turn</b>.</li>
							<li>On your turn, drop one of your colored discs from the top <i>into any of the seven slots</i>.</li>
							<li>The game ends when there are <b>4</b> of the <b>same colored</b> discs in a <i>row</i> (either <b>vertically</b>, <b>horizontally</b>, or <b>diagonally</b>) or a <b>stalemate</b>.</li>
							<li>The <i>starter</i> of the <i>previous</i> game goes <i>second</i> on the <i>next</i> game.</li>
						</ul>">
						Instructions
					</button>
				</div>
				<table className="table bg-secondary m-0">
					<thead style={{ borderColor: "white", borderWidth: 1 }}>
						<tr className="bg-white">
							<th id="arrow0"></th>
							<th id="arrow1"></th>
							<th id="arrow2"></th>
							<th id="arrow3"></th>
							<th id="arrow4"></th>
							<th id="arrow5"></th>
							<th id="arrow6"></th>
						</tr>
					</thead>
					<tbody>
						{Array.from({ length: 6 }, (v, i) => (
							<Row
								key={i}
								board={board}
								row={i}
								handleClick={handleClick}
								handleMouseEnter={handleMouseEnter}
								handleMouseLeave={handleMouseLeave}
							/>
						))}
					</tbody>
				</table>
			</div>
		</main>
	)
}