    .section .data
.LC0:
    .string "%d\n"                # String format for printf

    .section .text
    .globl main                   # Entry point for the program

square:
    pushq   %rbp                  # Save the base pointer
    movq    %rsp, %rbp            # Set the new base pointer
    movl    %edi, -4(%rbp)        # Store the argument (edi) into local variable (-4 offset)
    movl    -4(%rbp), %eax        # Move local variable (-4 offset) into eax
    imull   %eax, %eax            # Multiply eax by itself (square the value)
    popq    %rbp                  # Restore the base pointer
    ret                           # Return from the function

main:
    pushq   %rbp                  # Save the base pointer
    movq    %rsp, %rbp            # Set the new base pointer
    subq    $16, %rsp             # Allocate 16 bytes on the stack for local variables
    movl    %edi, -4(%rbp)        # Store argc (edi) in a local variable (-4 offset)
    movq    %rsi, -16(%rbp)       # Store argv (rsi) in a local variable (-16 offset)
    movl    $5, %edi              # Pass 5 as the argument to the square function
    call    square                # Call the square function
    movl    %eax, %esi            # Move the return value (squared result) into esi for printf
    leaq    .LC0(%rip), %rdi      # Load address of format string into rdi
    movl    $0, %eax              # Clear eax before calling printf (for variadic functions)
    call    printf                # Call printf
    movl    $0, %eax              # Set the return value for main
    leave                         # Clean up the stack
    ret                           # Return from main
