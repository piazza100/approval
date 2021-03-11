// import moment from 'moment'
import axios from 'axios'
import {msg} from './msg.js'
import {validate} from './validate.js'
import {api} from './api.js'
export const common = {
	mixins: [msg, validate, api],
	data () {
		return {
			isLoading : false,
			onProgress : false,

			userId: 'piazza100',
			password: '123qwe!@#',
			content:'',
			title:'',

			msg: '',

		}
	},
	filters: {
		formatDate: function (value) {
			if (value) {
				return moment(value).format('YYYY/MM/DD HH:mm:ss')
			}
		},
		formatDate: function (value, format) {
			if (value) {
				return moment(value).format(format)
			}
		},
		errorMessage(param, type) {
			if(param.length === 0){
				return ''
			}
			let msg = param.split('|')
			if ('CSS' === type) {
				if(msg[0] === 'Y'){
					return 'error_case2'
				}else{
					return 'error_case1'
				}
			}else if ('MSG' === type) {
				return msg[1]
			}
		},
	},
}