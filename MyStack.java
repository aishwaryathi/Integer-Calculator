    public String infixToPostfix() throws StackException {
        //infixExpression = infixExpression.replaceAll("\\s", "");

        StringBuilder post = new StringBuilder();
        MyStack<Character> stack = new MyArrayList<>();

        for(int i = 0; i<infixExpression.length();i++){
            Character ch = infixExpression.charAt(i);
            if(infixExpression.charAt(i)== ' ') {
                continue;
            }
            if(Character.isDigit(ch)) {
                post.append(ch);
                while(i+1<infixExpression.length() && Character.isDigit(infixExpression.charAt(i+1))) {
                    post.append(infixExpression.charAt(i+1));
                    i++;
                }
                post.append(" ");
            } else if(ch == '(') {
                stack.push('(');
            } else if(ch == ')') {
                while(!stack.isEmpty() && stack.peek()!='(') {
                    post.append(stack.pop());
                    post.append(" ");
                }
                stack.pop();
            }else if(ch =='~') {
                stack.push(ch);
            }else if(ch == '^' || ch =='x' || ch == '%' || ch =='/'
                    || ch == '+' || ch == '-') {
                int prec2 = 0;
//                int prec1 = 0;
//                int prec2 = 0;
//                if(ch == '~') {
//                    prec1 = unaryOperatorPrecedenceMap.get(ch);
//                } else {
//                    prec1 = binaryOperatorPrecedenceMap.get(ch);
//                }
//
                if(stack.isEmpty()==false && stack.peek()=='~') {
                    prec2 = unaryOperatorPrecedenceMap.get(stack.peek());
                } else if(stack.isEmpty()==false && stack.peek()!='~' && stack.peek()!='(') {
                    prec2 = binaryOperatorPrecedenceMap.get(stack.peek());
                }

                while((!stack.isEmpty()) && (binaryOperatorPrecedenceMap.get(ch) <= prec2) && stack.peek()!='(') {
                    if(stack.peek()=='^' && ch !='^') {
                        post.append(stack.pop());
                        post.append(" ");
                        break;
                    } else if(stack.peek()=='^' && ch=='^'){
                        break;
                    }else if(ch == '/') {
                        post.append(stack.pop());
                        post.append(" ");
                        break;
                    } else {
                        post.append(stack.pop());
                        post.append(" ");
                    }

                }
                stack.push(ch);

            }
        }
        while(!stack.isEmpty()) {
            post.append(stack.pop());
            post.append(" ");
        }

        String temp = post.toString();
//        String temp = "";
//        for(int i = 0; i <postfixExpression.length(); i++) {
//            temp = temp + postfixExpression.charAt(i) + " ";
//        }
        postfixExpression = temp.substring(0, temp.length()-1);

        return this.postfixExpression;


        // TODO

        // This is the last line of the method.
//        return this.postfixExpression;
    }

    /**
     * Evaluates the postfix expression and returns the integer value of the
     * expression. All operations are performed with integers.
     * @return the integer value that results after evaluating the postfix
     * expression
     * @throws StackException if an error occurs when calling a method on the
     * stack. This should not happen. The throws clause is there so that you
     * don't need any try-catch blocks in the body of this method.
     * @throws IllegalArgumentException if an attempt to divide or mod by zero
     * is encountered. The message of the exception reads:
     * "Cannot evaluate expression, division by zero."
     * An IllegalArgumentException si also thrown if the user attempts to
     * compute 0^0. The message of the exception reads:
     * "Cannot evaluate expression, 0^0 is undefined."
     */
    public int evaluatePostfix()
            throws StackException, IllegalArgumentException {


        MyStack<Integer> stack2 = new MyArrayList<>();
        //postfixExpression = postfixExpression.replaceAll("\\s", "");
        for(int i = 0; i < postfixExpression.length(); i++) {
            if(postfixExpression.charAt(i)== ' ') {
                continue;
            }
            Character ch = postfixExpression.charAt(i);
            if(Character.isDigit(ch)) {
                String temp = String.valueOf(ch);
                while(i+1<postfixExpression.length() && Character.isDigit(postfixExpression.charAt(i+1))) {
                    temp = temp + postfixExpression.charAt(i+1);
                    i++;
                }
                int operand = Integer.parseInt(temp);
                stack2.push(operand);
            } else if(ch == '~') {
                int negate = stack2.pop();
                negate = Math.negateExact(negate);
                stack2.push(negate);
            }else if(ch == '^' || ch =='x' || ch == '%' || ch =='/'
                    || ch == '+' || ch == '-') {
                int secondOp = stack2.pop();
                int firstOp = stack2.pop();
                int result = 0;
                if(ch == '^' ) {
                    if(firstOp == 0 && secondOp ==0) {
                        throw new IllegalArgumentException("Cannot evaluate expression, 0^0 is undefined.");
                    }
                    result = (int) Math.pow(firstOp, secondOp);
                } else if(ch =='x') {
                    result = firstOp*secondOp;
                }else if(ch == '%') {
                    if(secondOp ==0) {
                        throw new IllegalArgumentException("Cannot evaluate expression, division by zero.");
                    }
                    result = firstOp%secondOp;
                }else if(ch == '/') {
                    if(secondOp ==0) {
                        throw new IllegalArgumentException("Cannot evaluate expression, division by zero.");
                    }
                    result = firstOp/secondOp;
                }else if(ch == '+') {
                    result = firstOp+secondOp;
                }else if(ch == '-') {
                    result = firstOp - secondOp;
                }
                stack2.push(result);
            }

        }
        int finalVal = stack2.pop();
        return finalVal;

        // TODO
    }
