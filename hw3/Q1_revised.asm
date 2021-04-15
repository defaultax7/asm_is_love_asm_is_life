.data
inputMsg: .asciiz "\nInput R, G, B value of a point!\n"
redMsg: .asciiz "Red: (0-255)\n"
greenMsg: .asciiz "Green: (0-255)\n"
blueMsg: .asciiz "Blue: (0-255)\n"
grayscaleMsg: .asciiz "The grayscale is: "
rgb: .word 0:3

.text
.globl main
main:
	la $s0, rgb	#s0 = red
	la $s1, 4($s1)	#s1 = green
	la $s1, 8($s1)	#s2 = blue
	
	li $v0, 4
	la $a0, inputMsg
	syscall
	
	li $v0, 4
	la $a0, redMsg
	syscall
	
	li $v0,5
	syscall
	add $s0, $v0, $zero	
		
	li $v0, 4
	la $a0, greenMsg
	syscall
	
	li $v0,5
	syscall
	add $s1, $v0, $zero	
		
	li $v0, 4
	la $a0, blueMsg
	syscall
	
	li $v0,5
	syscall
	add $s2, $v0, $zero	
	
	add $a0, $s0, $zero
	add $a1, $s1, $zero
	add $a2, $s2, $zero
	jal rgb2gray
	
	add $s3, $v0, $zero
	
	li $v0, 4
	la $a0, grayscaleMsg
	syscall
	
	li $v0, 1
	add $a0, $s3, $zero
	syscall
	
	li $a0,52
	li $a1,103
	jal multiply
	
	
	j exit
	
## TODO BELOW
#input : $a0: red value
#	 $a1: green value
#	 $a2: blue value
#output: $v0: grayscale value
#hint: you can use the multiply function provided below 
#      to multiple two integers, the result will be in $v0
#You can add labels as you wish
#remember to preserve the save registers and the $ra according to slide 76 of https://course.cse.ust.hk/comp2611/note/COMP2611_ISA_Spring21.pdf       
rgb2gray:

	addi $sp , $sp , -4  
	sw $ra , ($sp) # push the return adress to the stack
	
	# backup variable $a0-$a2 
	addi $sp , $sp , -4  
	sw $a0 , ($sp) 	
	addi $sp , $sp , -4  
	sw $a1 , ($sp) 
	addi $sp , $sp , -4  
	sw $a2 , ($sp) 

	li $s0 , 0 # init grayscale to be 0	

	li $a1 , 19595 # red need to be multiplied by 19595
	jal multiply # call the function multiply for red
	
	add $s0 , $s0 , $v0 # add the multiplied red to sum
	
	lw $a0 , 4($sp) # calculate green this time
	li $a1 , 38469 # green need to be multiplied by 38469
	jal multiply # call the function multiply for red
	
	add $s0 , $s0 , $v0 # add the multiplied green to sum
	
	lw $a0 , 0($sp) # calculate blue this time
	li $a1 , 7472 # blue need to be multiplied by 7472
	jal multiply # call the function multiply for red
	
	add $s0 , $s0 , $v0 # add the multiplied blue to sum
	
	srl $s0 , $s0 , 16 # shift right for 16 bits
	
	la $v0 , ($s0) # return result
	
	# retrieve variable $a0-$a2 from stack
	lw $a2 , ($sp) 
	addi $sp , $sp , 4 
	lw $a1 , ($sp) 
	addi $sp , $sp , 4 
	lw $a0 , ($sp) 
	addi $sp , $sp , 4 
	
	lw $ra , ($sp) # load the return address from the stack
	addi $sp , $sp , 4 
	jr $ra
###TODO ABOVE


#mutiply()
#input : $a0: a
#	 $a1: b
#output: $v0: a*b
multiply:
	addi $sp, $sp, -4
	sw $s0, ($sp)
	li $s0, 0
	li $v0, 0
mult_loop:
	beq $s0, $a1, end_mult
	add $v0, $v0, $a0
	addi $s0, $s0, 1
	j mult_loop
end_mult:
	lw $s0, ($sp)
	addi $sp, $sp, 4
	jr $ra

exit:
	li $v0,10
	syscall
	
	


