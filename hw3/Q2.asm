.data
inputMsg: .asciiz "Type the string (with length at most 50):\n"
trueMsg: .asciiz "True!\n"
falseMsg: .asciiz "False!\n"
string: .byte 0:50

.text
.globl main
main:
	la $s0, string
	
	# print input message
	li $v0, 4
	la $a0, inputMsg
	syscall
	
	#read the string
	li $v0, 8
	add $a0, $s0, $zero #string
	li $a1, 50 #length
	syscall
	add $a0, $s0, $zero
	jal strlen
	add $s1, $v0, $zero
	
	add $a0, $s0, $zero
	add $a1, $s1, $zero
	jal isPalindrome
	add $s2, $v0, $zero
	
	bgtz $s2, printTrue 
	j printFalse
	
 ### TODO BELOW
 #input a0: the address of the string
 #output v0: the length
 #hints: 1. assume the last character of the string is a '\n' (with ascii value of 10)
 #       2. you just need to count the number of characters before encountering '\n'
 #       3. you can use lbu/lb to load the characters from the "string" array, 
 #          the base address is already in $a0. 
 #You can add labels as you wish
#remember to preserve the save registers and the $ra according to slide 76 of https://course.cse.ust.hk/comp2611/note/COMP2611_ISA_Spring21.pdf       
strlen:

        addi $sp , $sp , -4  
	sw $ra , ($sp) # push the return adress to the stack
	
	li $v0 , 0 # init the lenght  

	li $t1 , 10 # hold \n in t1 for checking
	loop:
	add $t2 , $a0 , $v0 # use t2 to point to next character 
	lbu $t0 , ($t2) # load the value of next character
	beq $t0 , $t1 , finish 
	addi $v0 , $v0 , 1  # add 1 count
	j loop # go back to check next character
	finish:
	
	lw $ra , ($sp) # load the return address from the stack
	addi $sp , $sp , 4 
	jr $ra
### TODO ABOVE
	

### TODO BELOW
# input: a0: address of string
#	 a1: length of string
# output:v0: 1 if true, 0 if false
#hints: refer to the C++ program for the logic
#You can add labels as you wish
#remember to preserve the save registers and the $ra according to slide 76 of https://course.cse.ust.hk/comp2611/note/COMP2611_ISA_Spring21.pdf       
isPalindrome:
	addi $sp , $sp , -4  
	sw $ra , ($sp) # push the return adress to the stack
	
	# back up the register $s0
	addi $sp , $sp , -4
	sw $s0 , ($sp) 
	
	li $s0 , 0 # init i
	loop2:
	srl $t0 , $a1 , 1 # n / 2
	slt $t2 , $s0 , $t0 # check i < n / 2
	beq $t2 , $zero , finish2 # if i is not < n / 2 , go to finish2
	
	add $t3 , $a0 , $s0 # $t3 is now pointing to str[i]
	lbu $t4 , ($t3) # load str[i] to $t4
	
	sub $t5 , $a1 , $s0 # $t4 =  n - i 
	addi $t5 , $t5 , -1 # $t4 = n - 1 - i
	add $t5 , $t5 , $a0 # $t5 is now pointing to str[n - 1 - i]
	lbu $t6 , ($t5) # load str[n - 1 - i] to $t6

	bne $t6 , $t4 , false  # if str[i] != str[n - 1 - i], return false
	
	addi $s0 , $s0 , 1 # i++
	j loop2 # go back to check next character
	
	false:
	li $v0 , 0 # it is false 
	finish2:
	
	
	# get back the original value of $s0
	lw $s0 , ($sp) 
	addi $sp , $sp , 4 

	lw $ra , ($sp) # load the return address from the stack
	addi $sp , $sp , 4 
	jr $ra
### TODO ABOVE
	
	
printFalse:
	li $v0,4
	la $a0,falseMsg
	syscall
	j exit
printTrue:
	li $v0,4
	la $a0,trueMsg
	syscall
	j exit
exit:
	li $v0,10
	syscall
	
	


