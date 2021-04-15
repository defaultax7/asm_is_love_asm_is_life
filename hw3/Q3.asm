.data
str:.byte 0:50
substr1: .byte 0:50
substr2: .byte 0:50

inputStrMsg:.asciiz "\nInput the string (with length at most 50): \n"
inputSubstr1Msg:.asciiz "Input the substring you want to replace: \n"
inputSubstr2Msg:.asciiz "Input the substring you want to replace with (in the same length): \n"
failMsg:.asciiz  "\nsubstring not found!"
successMsg: .asciiz "\nThe processed string is :"


.text
main:	
	la $s0, str  # s0 = address of target string
	la $s1, substr1     # s1 = address of substr1
	la $s2, substr2     # s2 = address of substr2

	# print input message
	li $v0,4
	la $a0, inputStrMsg
	syscall
	
        # getting the original/target string from user 
	add $a0, $s0, $zero
	li $a1, 50
	li $v0, 8
	syscall              
	
	li $v0,4
	la $a0, inputSubstr1Msg
	syscall
	
        # getting the substr1 from user 
	add $a0, $s1, $zero
	li $a1, 50
	li $v0, 8
	syscall               

	li $v0,4
	la $a0, inputSubstr2Msg
	syscall
	
	# getting the substr2 from user 
	add $a0, $s2, $zero
	li $a1, 50
	li $v0, 8
	syscall               
	

	add $a0,$s0,$zero #$a0 address of the target/original string	
	add $a1,$s1,$zero #$a1 the address of substr1
	add $a2,$s2,$zero #$a2 the address of substr1
	
	#pass $a0,$a1,$a2 to the function call
	#$a0 is address of the target/original string
	#$a1 is the address of substr1 (the substring to search for)
	#$a2 is the address of substr2 (the substring to replace substr1 with)
	jal findAndReplaceSubstring
	j end
	

####TODO BELOW
#inputs $a0:  address of the target/original string
#       $a1:  the address of substr1 (the substring to search for)
#       $a2:  the address of substr2 (the substring to replace substr1 with)
# hint: 1. you can call the strlen() to find the lengths of str, substr1 and substr2 as needed
#       2. you can use "lbu" to load a character
#       3. you can call replace() to do the substr replacement directly in str[], at the end just need to output str if replacement has been done
#You can add labels as you wish
#remember to preserve the save registers and the $ra according to slide 76 of https://course.cse.ust.hk/comp2611/note/COMP2611_ISA_Spring21.pdf       
findAndReplaceSubstring:

	addi $sp , $sp , -4
	sw $ra , ($sp) # push the return address to the stack
	
	# back up registers $s0-$s4 & $a0-$a2
	addi $sp , $sp , -4
	sw $s0 , ($sp)	
	addi $sp , $sp , -4
	sw $s1 , ($sp)	
	addi $sp , $sp , -4
	sw $s2 , ($sp)	
	addi $sp , $sp , -4
	sw $s3 , ($sp)	
	addi $sp , $sp , -4
	sw $s4 , ($sp)	
	addi $sp , $sp , -4
	sw $a0 , ($sp)	
	addi $sp , $sp , -4
	sw $a1 , ($sp)	
	addi $sp , $sp , -4
	sw $a2 , ($sp)	
	
	
	li $s0 , 0 # processd = false
	
	jal strlen  # count the length of the original string ($a0 is still the base address of original string)
	la $s1 , ($v0) # save the lenght of the original string to $s1

	lw $a0 , 4($sp) # pass the base address of the substring
	jal strlen  # count the length of the substring
	la $s2 , ($v0) # save the lenght of the substring to $s2

	li $s3 , 0 # int i = 0	
	loop2:
	slt $t0 , $s3 , $s1 # check i < length
	beq $t0 , $zero , exit2 # break the loop if it is not less than length
	
	lw $t0 , 8($sp) # point to the base address of the original string
	add $t0 , $t0 , $s3 # point to str[i]
	lbu $t0 , ($t0) # load the character in index i of the original string
	
	lw $t1 , 4($sp) # point to substring to search for
	lbu $t1 , ($t1) # load the first character of the substring to search for
	
	beq $t0 , $t1 , equalchar  # if the characters are equal , continue the step
	addi $s3 , $s3 , 1 # i++
	j loop2 # go to check next character
	equalchar:
	
	li $s4 , 0 # int j = 0
	loop3:
	slt $t0 , $s4 , $s2 # check if j < substrlength
	beq $t0 , $zero , exit3 # break the loop if j < substrlength is not true

	lw $t0 , 8($sp) # point to str[0]
	add $t0 , $t0 , $s3 # point to str[i]
	add $t0 , $t0 , $s4 # point to str[i+j]
	lbu $t0 , ($t0) # load character in str[i+j] 
	
	lw $t1 , 4($sp) # point to substr1[0]
	add $t1 , $t1 , $s4 # point to substr1[j]
	lbu $t1 , ($t1) # load character in substr1[j]
	
	bne $t0 , $t1 , exit3 # break the nested loop if(str[i+j] != substr1[j]
	
	addi $t0 , $s2 , -1 # save substrlength - 1 to $t0
	beq $s4 , $t0 , replacepro #  j == substrlength - 1 , start replacing the string
	addi $s4 , $s4 , 1 # j++
	j loop3 # check next character
	replacepro:
	
	# call replace
	lw $a0 , 8($sp) # base address of original string
	la $a1 , ($s3) # i
	lw $a2 , 0($sp) # base address of substr2
	la $a3 , ($s2) # length of the substring
	jal replace
	
	add $s3 , $s3 , $s2 # i = i + substrlenght
	addi $s3 , $s3 , -1 # i = i + substrlenght - 1
	li $s0 , 1 # processed = true
		
	addi $s4 , $s4 , 1 # j++
	j loop3
	
	exit3:
	
	addi $s3 , $s3 , 1 # i++
	j loop2
	
	exit2:
	
	
	# print message
	beq $s0 , $zero , notfound # if processed is false, go to notfound
	
	la $a0 , successMsg
	li $v0 , 4 
	syscall  # print the heading of the success message
	
	lw $a0 , 8($sp)
	li $v0 , 4 
	syscall # print the replaced message
	
	j exit5
	
	notfound:
	
	la $a0 , failMsg
	li $v0 , 4 
	syscall # print the substring not found message
	
	exit5:
	
	
	# load the value of registers $s0-s7 back
	lw $a2 , ($sp)
	addi $sp , $sp , 4
	lw $a1 , ($sp)
	addi $sp , $sp , 4
	lw $a0 , ($sp)
	addi $sp , $sp , 4
	lw $s4 , ($sp)
	addi $sp , $sp , 4
	lw $s3 , ($sp)
	addi $sp , $sp , 4
	lw $s2 , ($sp)
	addi $sp , $sp , 4
	lw $s1 , ($sp)
	addi $sp , $sp , 4
	lw $s0 , ($sp)
	addi $sp , $sp , 4
	
	
	lw $ra , ($sp) # load the return address back from the stack
	addi $sp , $sp , 4
 	jr $ra        
###TODO above 	


 #input $a0: the address of the string
 #output $v0: the length of the string
 #this is the same strlen() function as in Q2
 #*you can copy and paste the same function from Q2 to here*
 #TODO BELOW
 #add labels as you wish
 #remember to preserve the save registers and the $ra according to slide 76 of https://course.cse.ust.hk/comp2611/note/COMP2611_ISA_Spring21.pdf       
strlen:

	addi $sp , $sp , -4  
	sw $ra , ($sp) # push the return address to the stack	
	
	li $v0 , 0 # init the lenght  

	li $t1 , 10 # hold \n in t1 for checking
	loop:
	add $t2 , $a0 , $v0 # use t2 to point to next character 
	lbu $t0 , ($t2) # load the value of next character
	beq $t0 , $t1 , finish # if \n is found, stop counting
	addi $v0 , $v0 , 1  # add 1 count
	j loop # go back to check next character
	finish:
		
	lw $ra , ($sp) # load the return address from the stack
	addi $sp , $sp , 4 
        jr $ra
###TODO ABOVE
        
         	
 ### TODO BELOW
#inputs $a0: address of target string
#	$a1: index of where substr1 is found in the target string
#	$a2: address of substr2
#	$a3: length of substr2
#IMPORT! This function write directly to str[] updaing str[] by replacing substr1 starting at str[i] with substr2
#add labels as you wish
#remember to preserve the save registers and the $ra according to slide 76 of https://course.cse.ust.hk/comp2611/note/COMP2611_ISA_Spring21.pdf       
replace:

	addi $sp , $sp , -4  
	sw $ra , ($sp) # push the return address to the stack
	
	li $t0 , 0 # int = 0
	loop4:
	slt $t1 , $t0 , $a3 # check i < substrlrngth
	beq $t1 , $zero , exit4 # if i is not < substrlength , break the loop
	
	add $t1 , $a0 , $a1 # point to str[index]
	add $t1 , $t1 , $t0 # point to str[index + i]
	
	add $t2 , $a2 , $t0 # point to substr2[i]
	lbu $t2 , ($t2) # load the character in substr2[i]
	
	sb $t2 , ($t1) # str[index + i] = substr2[i]
	
	addi $t0 , $t0 , 1 # i++
	j loop4
	exit4:
			
	lw $ra , ($sp) # load the return address from the stack
	addi $sp , $sp , 4 
	
	jr $ra
### TODO ABOVE
	
#do not remove this label!	
end:
