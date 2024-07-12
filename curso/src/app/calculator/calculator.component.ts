/* eslint-disable @typescript-eslint/no-unused-expressions */
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-calculator',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './calculator.component.html',
  styleUrls: ['./calculator.component.css']
})
export class CalculatorComponent {
  currentNumber = '0';
  firstOperand: number | null = null;
  operator: string | null = null;
  waitForSecondNumber = false;
  maxDigits = 21;

  public getNumber(v: string) {
    if (this.waitForSecondNumber) {
      this.currentNumber = v;
      this.waitForSecondNumber = false;
    } else {
      if (this.currentNumber.length < this.maxDigits) {
        this.currentNumber === '0' ? this.currentNumber = v : this.currentNumber += v;
      }
    }
  }

  getDecimal() {
    if (!this.currentNumber.includes('.')) {
      if (this.currentNumber.length < this.maxDigits) {
        this.currentNumber += '.';
      }
    }
  }

  private doCalculation(op: string, secondOp: number): number {
    switch (op) {
      case '+':
        return (this.firstOperand || 0) + secondOp;
      case '-':
        return (this.firstOperand || 0) - secondOp;
      case '*':
        return (this.firstOperand || 0) * secondOp;
      case '/':
        return (this.firstOperand || 0) / secondOp;
      case '=':
        return secondOp;
      default:
        return secondOp;
    }
  }

  public getOperation(op: string) {
    if (this.firstOperand === null) {
      this.firstOperand = Number(this.currentNumber);
    } else if (this.operator) {
      const result = this.doCalculation(this.operator, Number(this.currentNumber));
      this.currentNumber = this.formatNumber(String(result));
      this.firstOperand = result;
    }

    this.operator = op;
    this.waitForSecondNumber = true;
  }

  public clear() {
    this.currentNumber = '0';
    this.firstOperand = null;
    this.operator = null;
    this.waitForSecondNumber = false;
  }

  public getFontSize(): string {
    const length = this.currentNumber.length;
    if (length > 10) {
      return '1.5em';
    } else if (length > 7) {
      return '2em';
    } else {
      return '2.5em';
    }
  }

  private formatNumber(num: string): string {
    if (num.length > this.maxDigits) {
      const formattedNumber = Number(num).toExponential(this.maxDigits - 5);
      return formattedNumber;
    }
    return num;
  }
}
